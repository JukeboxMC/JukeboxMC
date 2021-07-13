package org.jukeboxmc.command.validator;

import org.jukeboxmc.command.CommandParamType;
import org.jukeboxmc.command.CommandSender;

import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * @author Kaooot
 * @version 1.0
 */
public class StringValidator extends ParameterValidator<StringValidator, String> {

    private final Pattern pattern;

    public StringValidator( String regex ) {
        this.pattern = Pattern.compile( regex );
    }

    @Override
    public String validate( String input, CommandSender commandSender ) {
        if ( this.pattern.matcher( input ).matches() ) {
            return input;
        }

        return null;
    }

    @Override
    public String consume( Iterator<String> inputData ) {
        if ( inputData.hasNext() ) {
            return inputData.next();
        }

        return null;
    }

    @Override
    public CommandParamType getParameterType() {
        return CommandParamType.STRING;
    }

    @Override
    public boolean hasValues() {
        return false;
    }
}