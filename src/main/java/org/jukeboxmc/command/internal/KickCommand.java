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

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "kick" )
@Description ( "Kick a player with reason." )
@Permission ( "jukeboxmc.command.kick" )
public class KickCommand extends Command {

    public KickCommand() {
        super( CommandData.builder()
                .setParameters( new CommandParameter[]{
                        new CommandParameter( "target", CommandParamType.TARGET, false ),
                        new CommandParameter( "reason", CommandParamType.TEXT, true )
                } )
                .build() );
    }

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( args.length == 1 || args.length == 2 ) {
            Player target = Server.getInstance().getPlayer( args[0] );
            if ( target != null ) {
                String reason = args.length == 2 ? args[1] : "Kicked by admin.";
                target.kick( reason );
            } else {
                commandSender.sendMessage( "Target not found." );
            }
        } else {
            commandSender.sendMessage( "Target not found." );
        }
    }
}
