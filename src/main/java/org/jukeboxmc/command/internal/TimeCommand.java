package org.jukeboxmc.command.internal;

import com.nukkitx.protocol.bedrock.data.command.CommandParamType;
import org.apache.commons.lang3.math.NumberUtils;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandData;
import org.jukeboxmc.command.CommandParameter;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.gamerule.GameRule;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "time" )
@Description ( "Set the crurrent world time." )
@Permission ( "jukeboxmc.command.time" )
public class TimeCommand extends Command {

    public TimeCommand() {
        super( CommandData.builder()
                .setParameters(
                        new CommandParameter[]{
                                new CommandParameter( "start", List.of( "start", "stop" ), false )
                        },
                        new CommandParameter[]{
                                new CommandParameter( "set", List.of( "set" ), false ),
                                new CommandParameter( "time", List.of( "sunrise", "day", "noon", "sunset", "night", "midnight" ), false ),

                        }, new CommandParameter[]{
                                new CommandParameter( "set", List.of( "set" ), false ),
                                new CommandParameter( "time", CommandParamType.INT, false ),

                        }
                ).build() );
    }

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( commandSender instanceof Player player ) {
            if ( args.length == 1 ) {
                if ( args[0].equalsIgnoreCase( "start" ) ) {
                    if ( !player.getWorld().<Boolean>getGameRule( GameRule.DO_DAYLIGHT_CYCLE ) ) {
                        player.getWorld().setGameRule( GameRule.DO_DAYLIGHT_CYCLE, true );
                        player.sendMessage( "Time started." );
                    } else {
                        player.sendMessage( "§cThe time is already started." );
                    }
                } else if ( args[0].equalsIgnoreCase( "stop" ) ) {
                    if ( player.getWorld().getGameRule( GameRule.DO_DAYLIGHT_CYCLE ) ) {
                        player.getWorld().setGameRule( GameRule.DO_DAYLIGHT_CYCLE, false );
                        player.sendMessage( "Time stopped." );
                    } else {
                        player.sendMessage( "§cThe time is already started." );
                    }
                }
            } else if ( args.length == 2 ) {
                if ( args[0].equalsIgnoreCase( "set" ) ) {
                    if ( NumberUtils.isCreatable( args[1] ) ) {
                        int time = Integer.parseInt( args[1] );
                        player.getWorld().setWorldTime( time );
                        player.sendMessage( "Set the time to " + time );
                    } else {
                        switch ( args[1] ) {
                            case "sunrise" -> {
                                player.getWorld().setWorldTime( 23000 );
                                player.sendMessage( "Set the time to 23000" );
                            }
                            case "day" -> {
                                player.getWorld().setWorldTime( 1000 );
                                player.sendMessage( "Set the time to 1000" );
                            }
                            case "noon" -> {
                                player.getWorld().setWorldTime( 6000 );
                                player.sendMessage( "Set the time to 6000" );
                            }
                            case "sunset" -> {
                                player.getWorld().setWorldTime( 12000 );
                                player.sendMessage( "Set the time to 12000" );
                            }
                            case "night" -> {
                                player.getWorld().setWorldTime( 13000 );
                                player.sendMessage( "Set the time to 13000" );
                            }
                            case "midnight" -> {
                                player.getWorld().setWorldTime( 18000 );
                                player.sendMessage( "Set the time to 18000" );
                            }
                            default -> {
                            }
                        }
                    }
                }
            }
        } else {
            commandSender.sendMessage( "§cYou must be a player" );
        }
    }
}
