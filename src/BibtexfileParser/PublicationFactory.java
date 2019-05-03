package BibtexfileParser;

import BibtexfileParser.exceptions.LackOfRequiredArgumentException;
import BibtexfileParser.exceptions.InvalidPublicationException;
import BibtexfileParser.models.*;

import java.lang.reflect.Field;
import java.util.*;

/**
 * A class which creates particular publications and fulfills them on the basis of Entry content
 */

public class PublicationFactory {
    private static Map<String, Class<? extends IEntry>> classMap = new HashMap<>();

    static {
        classMap.put("article", Article.class);
        classMap.put("book", Book.class);
        classMap.put("conference", Conference.class);
        classMap.put("inbook", Inbook.class);
        classMap.put("booklet", Booklet.class);
        classMap.put("incollection", Incollection.class);
        classMap.put("manual", Manual.class);
        classMap.put("mastersthesis", Mastersthesis.class);
        classMap.put("misc", Misc.class);
        classMap.put("inproceedings", Inproceedings.class);
        classMap.put("phdthesis", Phdthesis.class);
        classMap.put("proceedings", Proceedings.class);
        classMap.put("techreport", Techreport.class);
        classMap.put("unpublished", Unpublished.class);
    }

    public static Map<String, Class<? extends IEntry>> getClassMap() {
        return classMap;
    }


    /**
     * Creates an instance of a class included in models package on the basis of given entry
     *
     * @param entry entry an entry which has to be transformed into an instance of special class
     * @return publication which will be added to bibtexfile instance
     * @throws InvalidPublicationException if publication doesnt exist
     * @throws LackOfRequiredArgumentException if the required field doesnt exist in an entry
     */
    public IEntry createPublication(Entry entry) throws InvalidPublicationException, LackOfRequiredArgumentException {
        String name = entry.getName();
        Class<? extends IEntry> clazz = classMap.get(name.toLowerCase());
        if (clazz == null) {
            throw new InvalidPublicationException(name);
        }
        IEntry publication = null;
        try {
            publication = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        publication.setCitationKey(entry.getCitationKey());
        for (String key : entry.getContent().keySet()) {
            Field field = null;
            try {
                field = clazz.getDeclaredField(key);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e + " in publication "+ publication.getName().substring(24));
            }

            if (field == null) {
                throw new LackOfRequiredArgumentException(field.getName(),publication.getName().substring(24));
            }
            field.setAccessible(true);
            if (!(key.equals("author")) && !(key.equals("editor"))) {
                try {
                    field.set(publication, entry.getContent().get(key));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    field.set(publication, getListOfAuthorsOrEditors(entry.getContent().get(key)));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        boolean isOneOfAlternativeFieldNull=false;
        boolean isOneOfAlternativeFieldInInbookNull = false;
        for (Field field : clazz.getDeclaredFields()) {
            Required required = field.getAnnotation(Required.class);
            try {
                field.setAccessible(true);
                if (required != null && field.get(publication) == null) {
                    throw new LackOfRequiredArgumentException(field.getName(),publication.getName().substring(24));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            Alternative alternative = field.getAnnotation(Alternative.class);
            try {
                field.setAccessible(true);
                if(alternative!=null && field.get(publication)==null){
                    if(!isOneOfAlternativeFieldNull){
                        isOneOfAlternativeFieldNull=true;
                    }
                    else {
                        throw new LackOfRequiredArgumentException(field.getName(),publication.getName().substring(24));
                    }
                }
            } catch (IllegalAccessException e){
                throw new RuntimeException(e);
            }

            AlternativeIn alternativeIn = field.getAnnotation(AlternativeIn.class);
            try {
                field.setAccessible(true);
                if(alternativeIn !=null && field.get(publication)==null){
                    if(!isOneOfAlternativeFieldInInbookNull){
                        isOneOfAlternativeFieldInInbookNull=true;
                    }
                    else {
                        throw new LackOfRequiredArgumentException(field.getName(),publication.getName().substring(24));
                    }
                }
            } catch (IllegalAccessException e){
                throw new RuntimeException(e);
            }

        }
        return publication;
    }

    /**
     * Creates a list of authors or editors which are hidden in entry as the whole string
     *
     * @param allAuthorsOrEditors string which has to be transformed into list of particular authors or editors
     * @return list with authors or editors as individual strings in format Name Surname
     */
    private List<String> getListOfAuthorsOrEditors(String allAuthorsOrEditors) {
        List<String> listOfAuthorsOrEditors = new ArrayList<>();
        if (allAuthorsOrEditors.contains(" and ")) {
            String[] authorsOrEditors = allAuthorsOrEditors.split(" and ");
            for (String authorOrEditor : authorsOrEditors) {
                listOfAuthorsOrEditors.add(makeTheOrderOfNamesProper(authorOrEditor));
            }
        } else {
            String authorOrEditor = allAuthorsOrEditors;
            listOfAuthorsOrEditors.add(makeTheOrderOfNamesProper(authorOrEditor));
        }
        return listOfAuthorsOrEditors;
    }

    /**
     * Standardizes the order of names, because they can be saved in different format
     *
     * @param authorOrEditor string with names which are not in a proper order
     * @return string in a form Name Surname
     */
    private String makeTheOrderOfNamesProper(String authorOrEditor) {
        //First von Last
        if (!authorOrEditor.contains("|")) {
            return authorOrEditor;
        }
        //von Last, First
        else if (authorOrEditor.indexOf("|") == authorOrEditor.lastIndexOf("|")) {
            String[] s = authorOrEditor.split("\\|", 2);
            if (s[1].startsWith(" ")) {
                StringBuilder a = new StringBuilder(s[1]);
                a.deleteCharAt(a.indexOf(" "));
                s[1]=a.toString();
            }
            StringBuilder sb = new StringBuilder(s[1]);
            sb.append(" ");
            sb.append(s[0]);
            return sb.toString();
        }
        //von Last, Jr., First
        else {
            String[] s = authorOrEditor.split("|", 3);
            if (s[2].startsWith(" ")) {
                s[2] = s[2].substring(1);
            }
            StringBuilder sb = new StringBuilder(s[2]);
            sb.append(" ");
            sb.append(s[0]);
            sb.append(",");
            sb.append(s[1]);
            return sb.toString();
        }
    }
}
