package BibtexfileParser.exceptions;

/**
 * Informs that there is no field which is required to particular publication
 */

public class LackOfRequiredArgumentException extends Exception {
    public LackOfRequiredArgumentException(String field, String publication) {
        super("Required field: "+field.toUpperCase()+" in "+publication+ " doesn't exist in file");
    }
}
