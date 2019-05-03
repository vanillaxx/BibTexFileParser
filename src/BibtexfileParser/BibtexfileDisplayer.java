package BibtexfileParser;

import BibtexfileParser.models.Entry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which makes it possible to show parsed bibtexfile as a table
 */

public class BibtexfileDisplayer {

    /**
     * It transforms instance of bibtexfile into a table which frame is made of a sign that we give
     *
     * @param bibtexFile an instance with ready to read publications
     * @param sign you can choose what sign can be a frame for our final table
     * @return table of publications with their content
     */
    public String displayBibtexfile(BibtexFile bibtexFile, String sign) {
        StringBuilder table = new StringBuilder("");
        for (int i = 0; i < 100; i++) {
            table.append(sign);
        }
        table.append('\n');
        for (IEntry publication : bibtexFile.getEntries()) {
            Class clazz = publication.getClass();
            String header = String.format("%s%s(%s)", sign, publication.getName().substring(24).toUpperCase(), publication.getCitationKey());
            table.append(String.format("%-99s%s\n", header, sign));
            for (int i = 0; i < 100; i++) {
                table.append(sign);
            }
            table.append("\n");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (!(field.getName().toLowerCase().equals("citationkey"))) {
                    try {
                        Object currentField = field.get(publication);
                        if (currentField != null) {
                            if (!(field.getName().equals("author")) && !(field.getName().equals("editor"))) {
                                if (currentField.toString().length() <= 82) {
                                    table.append(String.format("%s%-15s%s%-82s%s\n", sign, field.getName(), sign, currentField, sign));
                                    for (int i = 0; i < 100; i++) {
                                        table.append(sign);
                                    }
                                    table.append("\n");
                                }
                                else {
                                    String splittedText = currentField.toString();
                                    int mod=0;
                                    while(splittedText.substring(mod*82,(mod+1)*82).length()==82){
                                        table.append(String.format("%s%-15s%s%-82s%s\n", sign, field.getName(), sign, splittedText.substring(82*mod,82*(mod+1)), sign));
                                        mod++;
                                        if((mod+1)*82 >= splittedText.length()){
                                            break;
                                        }
                                    }
                                    if(splittedText.length() % 82 != 0){
                                    table.append(String.format("%s%-15s%s%-82s%s\n", sign, field.getName(), sign, splittedText.substring(82*mod,splittedText.length()), sign));
                                    }
                                    for (int i = 0; i < 100; i++) {
                                        table.append(sign);
                                    }
                                    table.append("\n");
                                }
                            } else {
                                switch (field.getName()) {
                                    case "author":
                                        table = addAuthorsToTable(table, currentField, sign, "author");
                                        break;
                                    case "editor":
                                        table = addAuthorsToTable(table, currentField, sign, "editor");
                                        break;
                                }
                            }
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return table.toString();
    }


    /**
     * adds authors or editors in a format to table
     *
     * @param table we have to add authors in another way because they are stored in list and it can be more than just one author
     * @param currentField it's just a field where authors or editors are stored
     * @param sign a sign which we chose
     * @param authorOrEditor an information which tells us if we add author or editor to table
     * @return table where authors are added
     */
    protected StringBuilder addAuthorsToTable(StringBuilder table, Object currentField, String sign, String authorOrEditor) {
        List<String> listOfAuthorsOrEditors = (List<String>) currentField;
        table.append(String.format("%s%-15s%s%-82s%s\n", sign, authorOrEditor, sign, listOfAuthorsOrEditors.get(0), sign));
        if (listOfAuthorsOrEditors.size() > 1) {
            for (int i = 1; i < listOfAuthorsOrEditors.size(); i++) {
                table.append(String.format("%-16s%s%-82s%s\n", sign, sign, listOfAuthorsOrEditors.get(i), sign));
            }
        }
        for (int i = 0; i < 100; i++) {
            table.append(sign);
        }
        return table.append("\n");
    }

    /**
     *looks for given category and returns table with publications which involve them
     *
     * @param bibtexFile ready to read instance of class which shows us bibtexfile
     * @param givenCategories a table of categories which we want to look for
     * @param sign our table will have a frame made of that character
     * @return table fulfilled with publications which belong to our categories
     */
    public String seekForCategoryAndDisplayBibtexfile(BibtexFile bibtexFile, String[] givenCategories, String sign) {
        BibtexFile bibtexfileWithCategory = new BibtexFile();
        List<IEntry> entriesOnlyWithCategory = new LinkedList<>();
        for (IEntry publication : bibtexFile.getEntries()) {
            String categoryOfPublication = publication.getName().substring(24);
            for (int i = 0; i < givenCategories.length; i++) {
                if (categoryOfPublication.toLowerCase().equals(givenCategories[i].toLowerCase()) && !entriesOnlyWithCategory.contains(publication)) {
                    entriesOnlyWithCategory.add(publication);
                }
            }
        }
        if(entriesOnlyWithCategory.isEmpty()){
            return "Sorry, no publications with given category found.";
        }
        bibtexfileWithCategory.setEntries(entriesOnlyWithCategory);
        return displayBibtexfile(bibtexfileWithCategory, sign);
    }

    /**
     *looks for authors and returns a table with publications which involve them
     *
     * @param bibtexFile bibtexFile ready to read instance of class which shows us bibtexfile
     * @param givenAuthors a table of authors which we want to look for
     * @param sign sign our table will have a frame made of that character
     * @return table fulfilled with publications which belong to our categories
     */
    public String seekForAuthorAndDisplayBibtexFile(BibtexFile bibtexFile, String[] givenAuthors, String sign) {
        BibtexFile bibtexFileWithAuthors = new BibtexFile();
        List<IEntry> entriesWithAuthors = new LinkedList<>();
        for (IEntry entry : bibtexFile.getEntries()) {
            if (entry instanceof HasAuthor) {
                HasAuthor publication = (HasAuthor) entry;
                if (publication.getAuthor() != null) {
                    for (String checkedAuthor : publication.getAuthor()) {
                        if (!entriesWithAuthors.contains(publication)) {
                            for (int i = 0; i < givenAuthors.length; i++) {
                                Matcher m = Pattern.compile(givenAuthors[i]).matcher(checkedAuthor);
                                if (m.find()) {
                                    entriesWithAuthors.add(entry);
                                }
                            }
                        }
                    }
                }
            }
        }
        if(entriesWithAuthors.isEmpty()){
            return "Sorry, no publications with given surname of author found.";
        }
        bibtexFileWithAuthors.setEntries(entriesWithAuthors);
        return displayBibtexfile(bibtexFileWithAuthors, sign);
    }

}
