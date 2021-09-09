package org.jukeboxmc.command.validator;

import org.jukeboxmc.command.CommandSender;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BooleanValidator extends EnumValidator {

    public BooleanValidator() {
        this.addValue( "true" );
        this.addValue( "false" );
    }

    @Override
    public String getName() {
        return "boolean";
    }

    @Override
    public Object parseObject( String value, CommandSender commandSender ) {
        return value.equals( "true" ) ? Boolean.TRUE : Boolean.FALSE;
    }
}
