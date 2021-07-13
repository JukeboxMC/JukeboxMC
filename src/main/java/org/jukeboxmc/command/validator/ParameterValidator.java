package org.jukeboxmc.command.validator;

import org.jukeboxmc.command.CommandParamType;
import org.jukeboxmc.command.CommandSender;

import java.util.Iterator;

/**
 * @author Kaooot
 * @version 1.0
 */
public abstract class ParameterValidator<T, E> {

    private boolean optional;
    private String postfix;

    /**
     * Validates the given input
     *
     * @param input         which should be validated
     * @param commandSender who submitted the command
     *
     * @return the expected validated object
     */
    public abstract E validate( String input, CommandSender commandSender );

    /**
     * Consumes all parts this {@link ParameterValidator} needs from the command input
     *
     * @param inputData which can be consumed
     *
     * @return a fresh {@link String} which represents the consumed data
     */
    public abstract String consume( Iterator<String> inputData );

    /**
     * Retrieves the {@link CommandParamType}
     *
     * @return a fresh {@link CommandParamType}
     */
    public abstract CommandParamType getParameterType();

    /**
     * Proofs whether the parameter has values
     *
     * @return if values are present
     */
    public abstract boolean hasValues();

    /**
     * Sets the optional state of the parameter
     *
     * @param optional which should be set
     *
     * @return a fresh {@link ParameterValidator}
     */
    public T setOptional( boolean optional ) {
        this.optional = optional;

        return (T) this;
    }

    /**
     * Retrieves if the parameter is optional
     *
     * @return whether param is optional
     */
    public boolean isOptional() {
        return this.optional;
    }

    /**
     * Sets the postfix value for the validator
     *
     * @param postfix which should be set
     *
     * @return a fresh {@link ParameterValidator}
     */
    public T setPostfix( String postfix ) {
        this.postfix = postfix;

        return (T) this;
    }

    /**
     * Retrieves the postfix of the validator
     *
     * @return postfix of validator
     */
    public String getPostfix() {
        return this.postfix;
    }
}