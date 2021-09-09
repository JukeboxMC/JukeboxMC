package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Alias;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "teleport" )
@Alias ( "tp" )
@Description ( "Teleport to another location or player." )
@Permission ( "org.jukeboxmc.command.teleport" )
public class TeleportCommand extends Command {

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( commandSender instanceof Player ) {
            Player player = (Player) commandSender;

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

                if ( tagetPlayer == null ){
                    player.sendMessage( "§cPlayer " + playerName + " could not be found" );
                    return;
                }

                if ( toPlayer == null ) {
                    player.sendMessage( "§cPlayer " + targetName + " could not be found" );
                    return;
                }

                tagetPlayer.teleport( toPlayer );
                player.sendMessage( "The player " + playerName + " has been teleported to " + targetName );
            } else if ( args.length == 3 ) {
                String number1 = args[0];
                String number2 = args[1];
                String number3 = args[2];

                if ( number1.isEmpty() || number2.isEmpty() || number3.isEmpty() ) {
                    player.sendMessage( "§cYou must specify a position" );
                    return;
                }

                try {
                    int x = Integer.parseInt( number1 );
                    int y = Integer.parseInt( number2 );
                    int z = Integer.parseInt( number3 );

                    player.teleport( new Vector( x, y, z ) );
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
