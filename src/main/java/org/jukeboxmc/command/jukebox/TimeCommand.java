package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.GameRule;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "time" )
@Description ( "Change the world time." )
@Permission ( "jukeboxmc.command.time" )
public class TimeCommand extends Command {

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( commandSender instanceof Player ) {
            Player player = (Player) commandSender;

            if ( args.length == 0 ) {
                player.sendMessage( "The current time is " + player.getWorld().getWorldTime() );
            } else if ( args.length == 1 ) {
                if ( args[0].equalsIgnoreCase( "stop" ) ) {
                    player.getWorld().setGameRule( GameRule.DO_DAYLIGHT_CYCLE, false );
                    player.getWorld().updateGameRules();
                    player.sendMessage( "World time was stopped" );
                } else if ( args[0].equalsIgnoreCase( "start" ) ) {
                    player.getWorld().setGameRule( GameRule.DO_DAYLIGHT_CYCLE, true );
                    player.getWorld().updateGameRules();
                    player.sendMessage( "World time was started" );
                }
            } else if ( args.length == 2 ) {
                if ( args[0].equalsIgnoreCase( "add" ) ) {
                    try {
                        int worldTime = Integer.parseInt( args[1] );
                        if ( worldTime > 24000 ) worldTime = 0;
                        player.getWorld().setWorldTime( player.getWorld().getWorldTime() + worldTime );
                        player.sendMessage( "Set time to " + worldTime );
                    } catch ( NumberFormatException e ) {
                        player.sendMessage( "§cYou must specify a number" );
                    }
                } else if ( args[0].equalsIgnoreCase( "set" ) ) {
                    try {
                        int worldTime = Integer.parseInt( args[1] );
                        if ( worldTime > 24000 ) worldTime = 0;
                        player.getWorld().setWorldTime( worldTime );
                        player.sendMessage( "Set time to " + worldTime );
                    } catch ( NumberFormatException e ) {
                        int worldTime;
                        if ( args[1].equalsIgnoreCase( "sunrise" ) ) {
                            worldTime = 23000;
                        } else if ( args[1].equalsIgnoreCase( "day" ) ) {
                            worldTime = 1000;
                        } else if ( args[1].equalsIgnoreCase( "noon" ) ) {
                            worldTime = 6000;
                        } else if ( args[1].equalsIgnoreCase( "sunset" ) ) {
                            worldTime = 12000;
                        } else if ( args[1].equalsIgnoreCase( "night" ) ) {
                            worldTime = 13000;
                        } else if ( args[1].equalsIgnoreCase( "midnight" ) ) {
                            worldTime = 18000;
                        } else {
                            return;
                        }
                        player.getWorld().setWorldTime( worldTime );
                        player.sendMessage( "Set time to " + worldTime );
                    }
                }
            }
        } else {
            commandSender.sendMessage( "§cYou must be a player" );
        }
    }
}
