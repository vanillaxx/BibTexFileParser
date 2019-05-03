package BibtexfileParser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Inform us that there is a pair of fields which are required but one of them may not exist
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Alternative {
}
