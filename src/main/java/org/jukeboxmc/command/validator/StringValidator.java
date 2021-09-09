package org.jukeboxmc.command.validator;

import org.jukeboxmc.command.CommandParamType;
import org.jukeboxmc.command.CommandSender;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class StringValidator extends Validator {

    @Override
    public Object parseObject( String value, CommandSender commandSender ) {
        return value;
    }

    @Override
    public CommandParamType getCommandParamType() {
        return CommandParamType.STRING;
    }

    @Override
    public String getName() {
        return "string";
    }
}
