package BibtexfileParser;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Made especially for inbook which is the only one which has chapter or pages as required
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface AlternativeIn {
}
