package org.jukeboxmc.command.validator;

import org.jukeboxmc.command.CommandParamType;
import org.jukeboxmc.command.CommandSender;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EnumValidator extends Validator {

    @Override
    public CommandParamType getCommandParamType() {
        return super.getCommandParamType();
    }

    @Override
    public Object parseObject( String value, CommandSender commandSender ) {
        return value;
    }
}
