package org.jukeboxmc.command.validator;

import org.jukeboxmc.Server;
import org.jukeboxmc.command.CommandParamType;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.player.Player;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author LucGamesYT, GoMint
 * @version 1.0
 */
public class TargetValidator extends Validator {

    @Override
    public CommandParamType getCommandParamType() {
        return CommandParamType.TARGET;
    }

    @Override
    public Object parseObject( String value, CommandSender commandSender ) {
        Collection<Player> onlinePlayers = Server.getInstance().getOnlinePlayers();

        for ( Player player : onlinePlayers ) {
            if ( player.getName().equalsIgnoreCase( value ) || player.getNameTag().equalsIgnoreCase( value ) ) {
                return player;
            }
        }

        return null;
    }

    @Override
    public String parseIterator( Iterator<String> data ) {
        // Check if we have one element left
        if ( !data.hasNext() ) {
            return null;
        }

        // The first element can either be " to signal that the name has spaces or its the player name itself
        String first = data.next();
        if ( first.startsWith( "\"" ) ) {
            if ( first.endsWith( "\"" ) ) {
                return first.substring( 1, first.length() - 1 );
            }

            StringBuilder nameBuilder = new StringBuilder( first.substring( 1 ) );
            while ( data.hasNext() ) {
                String current = data.next();
                if ( current.endsWith( "\"" ) ) {
                    nameBuilder.append( " " ).append( current, 0, current.length() - 1 );
                    return nameBuilder.toString();
                }

                nameBuilder.append( " " ).append( current );
            }

            return nameBuilder.toString();
        }

        return first;
    }

    @Override
    public String getName() {
        return "target:player";
    }
}
