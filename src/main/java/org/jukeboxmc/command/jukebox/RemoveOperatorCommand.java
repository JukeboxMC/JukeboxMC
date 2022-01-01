package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "deop" )
@Description ( "Remove player from operator" )
@Permission ( "jukeboxmc.command.removeoperator" )
public class RemoveOperatorCommand extends Command {

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( args.length == 1 ) {
            String playerName = args[0];

            if ( playerName != null && !playerName.isEmpty() ) {
                Player target = Server.getInstance().getPlayer( playerName );

                if ( target != null ) {
                    if ( !target.isOp() ) {
                        commandSender.sendMessage( "The player " + playerName + " is not an operator" );
                        return;
                    }
                    target.setOp( false );
                    target.sendMessage( "You are no longer an operator" );
                    Server.getInstance().removeOperatorFromFile( playerName );
                } else {
                    if ( Server.getInstance().isOperatorInFile( playerName ) ) {
                        Server.getInstance().removeOperatorFromFile( playerName );
                    } else {
                        commandSender.sendMessage( "The player " + playerName + " is not an operator" );
                    }
                }
                commandSender.sendMessage( "The player " + playerName + " is no longer an operator" );
            } else {
                commandSender.sendMessage( "§cYou must specify a name" );
            }
        }
    }
}
