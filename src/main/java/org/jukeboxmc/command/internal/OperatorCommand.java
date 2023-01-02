package org.jukeboxmc.command.internal;

import com.nukkitx.protocol.bedrock.data.command.CommandParamType;
import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandData;
import org.jukeboxmc.command.CommandParameter;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.player.Player;

import java.util.Collections;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "op" )
@Description ( "Add a player to operator" )
@Permission ( "jukeboxmc.command.operator" )
public class OperatorCommand extends Command {

    public OperatorCommand() {
        super( CommandData.builder()
                .setParameters( Collections.singletonList( new CommandParameter[]{
                        new CommandParameter( "player", CommandParamType.TARGET, false )
                } ) )
                .build() );
    }

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( args.length == 1 ) {
            String playerName = args[0];

            if ( playerName != null && !playerName.isEmpty() ) {
                Player target = Server.getInstance().getPlayer( playerName );

                if ( target != null ) {
                    if ( target.isOp() ) {
                        commandSender.sendMessage( "The player " + playerName + " is already an operator" );
                        return;
                    }
                    target.setOp( true );
                    target.sendMessage( "You are now operator" );
                    Server.getInstance().addOperatorToFile( target.getName() );
                } else {
                    if ( !Server.getInstance().isOperatorInFile( playerName ) ) {
                        Server.getInstance().addOperatorToFile( playerName );
                    } else {
                        commandSender.sendMessage( "The player " + playerName + " is already an operator" );
                    }
                }
                commandSender.sendMessage( "The player " + playerName + " is now an operator" );
            } else {
                commandSender.sendMessage( "§cYou must specify a name" );
            }
        }
    }
}
