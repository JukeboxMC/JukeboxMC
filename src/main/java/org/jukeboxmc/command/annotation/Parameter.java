package org.jukeboxmc.command.annotation;

import org.jukeboxmc.command.CommandParamType;
import org.jukeboxmc.command.validator.Validator;

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
     * Represents the parameter type of this {@link Parameter}
     *
     * @return a fresh {@link CommandParamType}
     */
    Class<? extends Validator> validator();

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