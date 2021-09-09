package jukeboxmc.command.validator;

import org.jukeboxmc.command.CommandParamType;
import org.jukeboxmc.command.CommandSender;

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
    public Object parseObject( String value, CommandSender commandSender ) {
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
