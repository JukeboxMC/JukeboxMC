package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandOutput;
import org.jukeboxmc.command.CommandParamType;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.*;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;

import java.util.Map;

/**
 * @author Kaooot
 * @version 1.0
 */
@Name ( "gamemode" )
@Alias ( "gm" )
@Description ( "Updates your gamemode | experimental" )
@Permission ( "jukeboxmc.gamemode" )
@ParameterSection (
        @Parameter ( name = "mode", type = CommandParamType.INT, arguments = "mode" )
)
public class GameModeCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender commandSender, String alias, Map<String, Object> arguments ) {
        if ( commandSender instanceof Player ) {
            Player player = (Player) commandSender;

            player.setGameMode( player.getGameMode().equals( GameMode.CREATIVE ) ? GameMode.SURVIVAL : GameMode.CREATIVE );
        }

        return new CommandOutput().success( "Your gamemode has been updated" );
    }
}