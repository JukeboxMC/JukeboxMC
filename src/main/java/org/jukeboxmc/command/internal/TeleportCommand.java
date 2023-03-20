package org.jukeboxmc.command.internal;

import com.nukkitx.protocol.bedrock.data.command.CommandParamType;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandData;
import org.jukeboxmc.command.CommandParameter;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Dimension;

import java.util.Arrays;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "teleport" )
@Description ( "Teleport to another location or player." )
@Permission ( "jukeboxmc.command.teleport" )
public class TeleportCommand extends Command {

    public TeleportCommand() {
        super( CommandData.builder()
                .addAlias( "tp" )
                .setParameters( new CommandParameter[]{
                                new CommandParameter( "player", CommandParamType.TARGET, false )
                        },
                        new CommandParameter[]{
                                new CommandParameter( "position", CommandParamType.POSITION, false ),
                                new CommandParameter( "dimension", Arrays.asList( "overworld", "nether", "the_end" ), true )
                        },
                        new CommandParameter[]{
                                new CommandParameter( "player", CommandParamType.TARGET, false ),
                                new CommandParameter( "target", CommandParamType.TARGET, false )
                        }
                )
                .build() );
    }

    @Override
    public void execute( CommandSender commandSender, String command, String @NotNull [] args ) {
        if ( commandSender instanceof Player player ) {
            if ( args.length == 1 ) {
                String targetName = args[0];
                if ( targetName.isEmpty() ) {
                    player.sendMessage( "§cYou must specify a name" );
                    return;
                }

                Player target = Server.getInstance().getPlayer( targetName );
                if ( target == null ) {
                    player.sendMessage( "§cThe player " + targetName + " could not be found" );
                    return;
                }
                player.teleport( target );
                player.sendMessage( "You have been teleported to " + targetName );
            } else if ( args.length == 2 ) {
                String playerName = args[0];
                String targetName = args[1];

                if ( playerName.isEmpty() || targetName.isEmpty() ) {
                    player.sendMessage( "§cYou must specify a name for both players" );
                    return;
                }

                Player tagetPlayer = Server.getInstance().getPlayer( playerName );
                Player toPlayer = Server.getInstance().getPlayer( targetName );

                if ( tagetPlayer == null ) {
                    player.sendMessage( "§cPlayer " + playerName + " could not be found" );
                    return;
                }

                if ( toPlayer == null ) {
                    player.sendMessage( "§cPlayer " + targetName + " could not be found" );
                    return;
                }

                tagetPlayer.teleport( toPlayer );
                player.sendMessage( "The player " + playerName + " has been teleported to " + targetName );
            } else if ( args.length == 3 || args.length == 4 ) {
                String number1 = args[0];
                String number2 = args[1];
                String number3 = args[2];
                String dimension = null;
                if ( args.length == 4 ) {
                    dimension = args[3];
                }

                if ( number1.isEmpty() || number2.isEmpty() || number3.isEmpty() ) {
                    player.sendMessage( "§cYou must specify a position" );
                    return;
                }

                try {
                    int x = Integer.parseInt( number1 );
                    int y = Integer.parseInt( number2 );
                    int z = Integer.parseInt( number3 );

                    player.teleport( new Location( player.getWorld(), new Vector( x, y, z ), dimension == null ? player.getDimension() : Dimension.valueOf( dimension.toUpperCase() ) ) );
                    player.sendMessage( "You have benn teleported to " + x + ", " + y + ", " + z );
                } catch ( NumberFormatException e ) {
                    player.sendMessage( "§cYou must specify a number" );
                }
            }
        } else {
            commandSender.sendMessage( "§cYou must be a player" );
        }
    }
}
