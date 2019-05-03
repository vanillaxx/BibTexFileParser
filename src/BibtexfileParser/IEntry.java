package BibtexfileParser;

/**
 * Interface which is implemented by every class which contains name and citation key so is an entry and every publication.
 */

public interface IEntry {
    String getCitationKey();
    void setCitationKey(String key);
    default String getName(){
        return getClass().getName();
    }

}
