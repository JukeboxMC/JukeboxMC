package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Alias;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
@Name ( "gamemode" )
@Alias ( "gm" )
@Description ( "Updates your gamemode | experimental" )
@Permission ( "org.jukeboxmc.command.gamemode" )
public class GameModeCommand extends Command {

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( commandSender instanceof Player ) {
            Player player = (Player) commandSender;

            if ( args.length == 1 ) {
                String gamemodeName = args[0];

                if ( gamemodeName == null || gamemodeName.isEmpty() ) {
                    player.sendMessage( "§cYou must specify a gamemode" );
                    return;
                }

                GameMode gameMode = this.getGameModeByName( gamemodeName );
                if ( gameMode == null ) {
                    player.sendMessage( "§cGamemode " + gamemodeName + " not found." );
                    return;
                }

                player.setGameMode( gameMode );
                player.sendMessage( "Your game mode has been updated to " + gameMode.getIdentifier() );
            } else if ( args.length == 2 ) {
                String gamemodeName = args[0];
                String targetName = args[1];

                if ( gamemodeName == null || gamemodeName.isEmpty() ) {
                    player.sendMessage( "§cYou must specify a gamemode" );
                    return;
                }

                if ( targetName == null || targetName.isEmpty() ) {
                    player.sendMessage( "§cYou must specify a player" );
                    return;
                }

                Player target = Server.getInstance().getPlayer( targetName );
                if ( target == null ) {
                    player.sendMessage( "§cThe player " + targetName + " could not be found" );
                    return;
                }
                GameMode gameMode = this.getGameModeByName( gamemodeName );
                if ( gameMode == null ) {
                    player.sendMessage( "§cGamemode " + gamemodeName + " not found." );
                    return;
                }
                target.setGameMode( gameMode );
                target.sendMessage( "Your game mode has been updated to " + gameMode.getIdentifier() );

                if ( target == commandSender ) {
                    player.sendMessage( "Set own game mode to " + gameMode.getIdentifier() );
                } else {
                    player.sendMessage( "Set " + target.getName() + "'s game mode to " + gameMode.getIdentifier() );
                }
            } else {
                player.sendMessage( "§cUsage: /gamemode <gamemode> [target]" );
            }
        }
    }

    private GameMode getGameModeByName( String value ) {
        GameMode gameMode = null;
        switch ( value ) {
            case "survival":
            case "0":
                gameMode = GameMode.SURVIVAL;
                break;
            case "creative":
            case "1":
                gameMode = GameMode.CREATIVE;
                break;
            case "adventure":
            case "2":
                gameMode = GameMode.ADVENTURE;
                break;
            case "spectator":
            case "3":
                gameMode = GameMode.SPECTATOR;
                break;
            default:
                break;
        }
        return gameMode;
    }
}