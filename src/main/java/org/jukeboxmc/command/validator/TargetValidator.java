package org.jukeboxmc.command.validator;

import org.jukeboxmc.Server;
import org.jukeboxmc.command.CommandParamType;
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
    public Object parseObject( String value ) {
        Collection<Player> onlinePlayers = Server.getInstance().getOnlinePlayers();

        for ( Player player : onlinePlayers ) {
            if ( player.getName().equalsIgnoreCase( value ) || player.getNameTag().equalsIgnoreCase( value ) ) {
                return player;
            }
        }

        return null;
    }

    @Override
    public String getName() {
        return "target:player";
    }
}
