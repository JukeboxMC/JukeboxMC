package org.jukeboxmc.command.validator;

import org.jukeboxmc.command.CommandParamType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FloatValidator extends Validator {

    @Override
    public Object parseObject( String value ) {
        try {
            return Float.parseFloat( value );
        } catch ( NumberFormatException e ) {
            return null;
        }
    }

    @Override
    public CommandParamType getCommandParamType() {
        return CommandParamType.FLOAT;
    }

    @Override
    public String getName() {
        return "float";
    }
}
