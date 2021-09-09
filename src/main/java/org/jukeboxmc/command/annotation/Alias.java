package org.jukeboxmc.command.annotation;

import java.lang.annotation.*;

/**
 * @author Kaooot
 * @version 1.0
 */
@Target ( ElementType.TYPE )
@Repeatable ( Aliases.class )
@Retention ( RetentionPolicy.RUNTIME )
public @interface Alias {

    /**
     * Represents one of the aliases of a {@link org.jukeboxmc.command.Command}
     *
     * @return a fresh {@link String}
     */
    String value() default "";
}