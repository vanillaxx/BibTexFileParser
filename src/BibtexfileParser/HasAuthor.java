package BibtexfileParser;

import java.util.List;

/**
 * A class which implements that interface must have an author so that it will be easier to look for them.
 */

public interface HasAuthor {
    List<String> getAuthor();
}
