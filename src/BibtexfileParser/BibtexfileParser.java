
package BibtexfileParser;

import BibtexfileParser.exceptions.LackOfRequiredArgumentException;
import BibtexfileParser.exceptions.InvalidPublicationException;
import BibtexfileParser.models.Entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class created to parse bibtex files on the basis of their filepath
 */

public class BibtexfileParser {
    /**
     * Parses the given file
     *
     * @param filePath path to the file which is meant to be parsed
     * @return BibtexFile instance fulfilled with publications included in package models (apart from Entry)
     * @throws InvalidPublicationException because of the wrong name of publication in file
     * @throws LackOfRequiredArgumentException if the field required to the special publication does not exist
     */
    public BibtexFile parse(String filePath) throws InvalidPublicationException, LackOfRequiredArgumentException {
        String file = readFileAsString(filePath);
        String[] records = file.split("@");
        List<Entry> listOfEntries = makeListOfEntries(records);
        BibtexFile newBibtexFile = new BibtexFile();
        PublicationFactory factory = new PublicationFactory();
        List<IEntry> instances = new ArrayList<>();
        for (Entry entry : listOfEntries) {
            instances.add(factory.createPublication(entry));
        }
        newBibtexFile.setEntries(instances);
        return newBibtexFile;
    }

    /**
     * Reads given file and returns it as a string
     *
     * @param fileName path to the file
     * @return the whole file as string
     */
    protected String readFileAsString(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            System.out.println("Problem z odczytaniem pliku");
        }
        return null;
    }

    private static final List<String> ALLOWED_PUBLICATIONS = List.of("ARTICLE","BOOK","BOOKLET","CONFERENCE","INBOOK","INCOLLECTION","INPROCEEDINGS","MANUAL","MASTERSTHESIS","MISC","PHDTHESIS","PROCEEDINGS","TECHREPORT","UNPUBLISHED");

    /**
     * Creates a properly filfilled list of entries
     *
     * @param records represents table of potential records (file splitted by "@" char)
     * @return list of entries also with fields which are cross-referenced
     * @throws InvalidPublicationException when the name of publication is invalid
     */
    protected List<Entry> makeListOfEntries(String[] records) throws InvalidPublicationException {
        List<Entry> listOfEntries = new LinkedList<>();
        Map<String, String> mapOfStringVariables = new HashMap<>();
        Map<String, Entry> mapOfEntriesWithCrossReferences = new HashMap<>();
        for (int i = 0; i < records.length; i++) {
            String record = records[i].toUpperCase();
            if (record.startsWith("PREAMBLE") || record.startsWith("COMMENT")) {
                continue;
            }
            int openingBrace = record.indexOf("{");
            if(openingBrace<0){
                continue;
            }
            String name = record.substring(0,openingBrace);
            if(name.equalsIgnoreCase("STRING")){
                addVariableToMapOfStringVariables(records[i],mapOfStringVariables);
                continue;
            }
            if(!ALLOWED_PUBLICATIONS.contains(name)){
                throw new InvalidPublicationException((record.substring(0,15)) + "(...) in record number "+ String.valueOf(i));
            }
            Entry entry = new Entry();
            entry.setName(name);
            listOfEntries.add(entry);
            addCitationKeyAndContent(listOfEntries,records[i],mapOfStringVariables,mapOfEntriesWithCrossReferences);
        }
        if (!mapOfEntriesWithCrossReferences.isEmpty()) {
            fulfillEntriesWithCrossReferences(listOfEntries, mapOfEntriesWithCrossReferences);
        }
        return listOfEntries;
    }

    /**
     * Adds the recognised variable to the map in a proper way. First parameter in map is the name of variable and the second is the content of variable.
     *
     * @param variable record which starts from @STRING{(...)}
     * @param mapOfStringVariables consists of name of variable as key and the content
     */
    protected void addVariableToMapOfStringVariables(String variable, Map<String, String> mapOfStringVariables) {
        Matcher m = Pattern.compile("[{][^}]+}").matcher(variable);
        m.find();
        variable = m.group();
        StringBuilder sb = new StringBuilder(variable);
        sb.deleteCharAt(sb.lastIndexOf("}"));
        sb.deleteCharAt(sb.indexOf("{"));
        variable = removeWhiteSpaces(sb).toString();
        sb = new StringBuilder("");
        if (!variable.contains("#")) {
            String[] variableAndContent = variable.split("[=\"]+");
            mapOfStringVariables.put(variableAndContent[0], variableAndContent[1]);
        } else {
            String[] variableAndContent = variable.split("=");
            String[] contentWithoutHashes = variableAndContent[1].split("#");
            for (int i = 0; i < contentWithoutHashes.length; i++) {
                if (!contentWithoutHashes[i].contains("\"")) {
                    contentWithoutHashes[i].replace(contentWithoutHashes[i], mapOfStringVariables.get(contentWithoutHashes[i]));
                    sb.append(contentWithoutHashes[i]);
                } else {
                    StringBuilder s = new StringBuilder(contentWithoutHashes[i]);
                    //delete quotationmarks
                    s.deleteCharAt(s.length() - 1);
                    s.deleteCharAt(0);
                    sb.append(s);
                }
            }
            variableAndContent[1] = sb.toString();
            mapOfStringVariables.put(variableAndContent[0], variableAndContent[1]);
        }
    }

    /**
     * Adds a citationKey and other fields with their content to an existing entry with name fulfilled
     *
     * @param listOfEntries a list where entries are stored
     * @param record a record which we are sure of being a publication
     * @param mapOfStringVariables map with string variables which we have to replace in content
     * @param mapOfEntriesWithCrossReferences stores a citationkey of publication which is cross-referenced as key
     *                                        and the publication which has to be fulfilled with crossreferenced fields
     */
    protected void addCitationKeyAndContent(List<Entry> listOfEntries, String record, Map<String, String> mapOfStringVariables, Map<String, Entry> mapOfEntriesWithCrossReferences) {
        Map<String, String> mapOfContent = new HashMap<>();
        StringBuilder s = new StringBuilder(record);
        record = removeWhiteSpaces(s).toString();
        Pattern p = Pattern.compile("[{][^}]+}");
        Matcher matcher = p.matcher(record);
        matcher.find();
        String recordContent = matcher.group();
        String[] citationKeyAndContent = recordContent.split(",", 2);
        listOfEntries.get(listOfEntries.size() - 1).setCitationKey(citationKeyAndContent[0].substring(1));
        StringBuilder sb = new StringBuilder(citationKeyAndContent[1]);
        String content = removeRightBrace(removeWhiteSpaces(sb)).toString();
        String[] contentTable = content.split(",");
        for (int i = 0; i < contentTable.length; i++) {
            String[] fieldAndContent = contentTable[i].split("[=]+", 2);
            if (fieldAndContent[0].equals("crossref")) {
                mapOfEntriesWithCrossReferences.put(fieldAndContent[1].substring(1, fieldAndContent[1].length() - 1), listOfEntries.get(listOfEntries.size() - 1));
            } else if (fieldAndContent[1].contains("\"") && !fieldAndContent[1].contains("#")) {
                fieldAndContent[1] = fieldAndContent[1].substring(1, fieldAndContent[1].length() - 1);
                mapOfContent.put(fieldAndContent[0].toLowerCase(), fieldAndContent[1]);
            } else {
                fieldAndContent[1] = replaceVariables(mapOfStringVariables, fieldAndContent[1]);
                mapOfContent.put(fieldAndContent[0].toLowerCase(), fieldAndContent[1]);
            }

        }
        listOfEntries.get(listOfEntries.size() - 1).setContent(mapOfContent);
    }

    /**
     * Replaces variables on the basis of their name
     *
     * @param mapOfStringVariables stores the variables from file
     * @param content a field which we are sure that contains an expression to replace
     * @return content which has already replaced variables
     */
    protected String replaceVariables(Map<String, String> mapOfStringVariables, String content) {
        if (content.contains("#")) {
            StringBuilder finalContent = new StringBuilder("");
            String[] contentWithoutHashes = content.split("#");
            for (int i = 0; i < contentWithoutHashes.length; i++) {
                if (contentWithoutHashes[i].contains("\"")) {
                    StringBuilder sb = new StringBuilder(contentWithoutHashes[i]);
                    sb.deleteCharAt(sb.length() - 1);
                    sb.deleteCharAt(0);
                    finalContent.append(sb);
                } else {
                    contentWithoutHashes[i] = mapOfStringVariables.get(contentWithoutHashes[i]);
                    finalContent.append(contentWithoutHashes[i]);
                }
            }
            return finalContent.toString();
        } else {
            return mapOfStringVariables.get(content);
        }
    }

    /**
     * Removes the right brace from a record
     *
     * @param sb an expression, where we have to get rid of the right brace
     * @return a string builder without the right brace
     */
    protected StringBuilder removeRightBrace(StringBuilder sb) {
        sb.deleteCharAt(sb.lastIndexOf("}"));
        return sb;
    }

    /**
     * Removes white space in every place which is beyond the quotation marks
     *
     * @param file we have to get rid of white spaces in record to make it easier to parse file
     * @return a record without white spaces
     */

    protected StringBuilder removeWhiteSpaces(StringBuilder file) {
        int quotationMarks = 0;
        for (int i = 0; i < file.length(); i++) {
            if (file.charAt(i) == '\"') {
                if (quotationMarks == 0) {
                    quotationMarks++;
                } else if (quotationMarks == 1) {
                    quotationMarks--;
                }
            }
            if (file.charAt(i) == ' ' || file.charAt(i) == '\n' || file.charAt(i) == '\t') {
                if (quotationMarks == 0) {
                    file.deleteCharAt(i);
                    i--;
                }
            }
        }
        return file;
    }

    /**
     * Adds fields which are cross-referenced to a publication
     *
     * @param listOfEntries entries which are already fulfilled
     * @param mapOfEntriesWithCrossReferences an information which tells us which entries refer to other
     */

    protected void fulfillEntriesWithCrossReferences(List<Entry> listOfEntries, Map<String, Entry> mapOfEntriesWithCrossReferences) {
        for (String referencedKey : mapOfEntriesWithCrossReferences.keySet()) {
            for (int i = 0; i < listOfEntries.size(); i++) {
                Entry checkedEntry = listOfEntries.get(i);
                if (checkedEntry.getCitationKey().equals(referencedKey)) {
                    for (String field : checkedEntry.getContent().keySet()) {
                        if (mapOfEntriesWithCrossReferences.get(referencedKey).getContent().get(field) == null) {
                            mapOfEntriesWithCrossReferences.get(referencedKey).getContent().put(field, checkedEntry.getContent().get(field));
                        }
                    }
                }
            }
        }
    }

}
