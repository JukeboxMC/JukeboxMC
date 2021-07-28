package org.jukeboxmc.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kaooot
 * @version 1.0
 */
@Target ( ElementType.TYPE )
@Retention ( RetentionPolicy.RUNTIME )
public @interface Aliases {

    /**
     * Represents all {@link Alias} values of a {@link org.jukeboxmc.command.Command}
     *
     * @return a fresh {@link String}
     */
    Alias[] value();
}