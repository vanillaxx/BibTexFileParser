package BibtexfileParser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Informs that field must exist in publication
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Required {

}
