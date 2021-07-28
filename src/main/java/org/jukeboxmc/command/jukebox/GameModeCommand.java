package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandOutput;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.*;
import org.jukeboxmc.command.validator.EnumValidator;
import org.jukeboxmc.command.validator.TargetValidator;
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
@Permission ( "jukeboxmc.command.gamemode" )
@ParameterSection ( {
        @Parameter ( name = "gamemode", validator = EnumValidator.class, arguments = { "survival", "creative", "adventure", "spectator" } ),
        @Parameter ( name = "player", validator = TargetValidator.class , optional = true )

} )
public class GameModeCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender commandSender, String command, Map<String, Object> arguments ) {
        if ( commandSender instanceof Player ) {
            Player player = (Player) commandSender;
            Player target = (Player) arguments.getOrDefault( "player", player );

            GameMode gameMode = GameMode.SURVIVAL;
            if ( arguments.containsKey( "gamemode" ) ) {
                if ( arguments.get( "gamemode" ) == null ) {
                    return new CommandOutput().fail( "Could not found gamemode." );
                }
                String gamemode = String.valueOf( arguments.get( "gamemode" ) );
                switch ( gamemode ) {
                    case "creative":
                        gameMode = GameMode.CREATIVE;
                        break;
                    case "survival":
                        gameMode = GameMode.SURVIVAL;
                        break;
                    case "adventure":
                        gameMode = GameMode.ADVENTURE;
                        break;
                    case "spectator":
                        gameMode = GameMode.SPECTATOR;
                        break;
                    default:
                        return new CommandOutput().fail( "Gamemode " + gamemode + " not found." );
                }
            }
            target.setGameMode( gameMode );
            target.sendMessage( "Your game mode has been updated to " + gameMode.getGamemode() );

            if ( target == commandSender ) {
                return new CommandOutput().success( "Set own game mode to " + gameMode.getGamemode() );
            } else {
                return new CommandOutput().success( "Set " + target.getName() + "'s game mode to " + gameMode.getGamemode() );
            }
        }
        return new CommandOutput();
    }
}