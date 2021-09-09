package jukeboxmc.command.validator;

import org.jukeboxmc.command.CommandParamType;
import org.jukeboxmc.command.CommandSender;

import java.util.Iterator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TextValidator extends Validator {

    @Override
    public String parseIterator( Iterator<String> data ) {
        StringBuilder forValidator = new StringBuilder();
        while ( data.hasNext() ) {
            forValidator.append( data.next() ).append( " " );
        }

        if ( forValidator.length() > 0 ) {
            return forValidator.deleteCharAt( forValidator.length() - 1 ).toString();
        }

        return null;
    }

    @Override
    public Object parseObject( String value, CommandSender commandSender ) {
        return value;
    }

    @Override
    public CommandParamType getCommandParamType() {
        return CommandParamType.RAWTEXT;
    }

    @Override
    public String getName() {
        return "text";
    }
}
