package BibtexfileParser.exceptions;

/**
 * Exception to inform if there was a problem with parsing name of publication
 */

public class InvalidPublicationException extends Exception {
    public InvalidPublicationException(String publication) {
        super(publication + " was not recognized");
    }
}
