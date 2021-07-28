package org.jukeboxmc.command.validator;

import org.jukeboxmc.command.CommandParamType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class IntegerValidator extends Validator {

    @Override
    public CommandParamType getCommandParamType() {
        return CommandParamType.INT;
    }

    @Override
    public Object parseObject( String value ) {
        try {
            return Integer.parseInt( value );
        } catch ( NumberFormatException e ) {
            return null;
        }
    }

    @Override
    public String getName() {
        return "int";
    }
}
