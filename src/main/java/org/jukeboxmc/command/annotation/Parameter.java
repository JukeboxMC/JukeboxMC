package org.jukeboxmc.command.annotation;

import org.jukeboxmc.command.validator.ParameterValidator;

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
public @interface Parameter {

    /**
     * Represents the name of this {@link Parameter}
     *
     * @return a fresh {@link String}
     */
    String name();

    /**
     * Represents the {@link ParameterValidator} of this {@link Parameter}
     *
     * @return a fresh {@link Class<? extends ParameterValidator>}
     */
    Class<? extends ParameterValidator<?, ?>> validator();

    /**
     * Represents the list of arguments of this {@link Parameter}
     *
     * @return a fresh {@link String} array
     */
    String[] arguments() default {};

    /**
     * Represents whether this {@link Parameter} is optional
     *
     * @return whether this {@link Parameter} is optional
     */
    boolean optional() default false;

    /**
     * Represents the postfix value for this {@link Parameter}
     *
     * @return a fresh {@link String}
     */
    String postfix() default "";
}