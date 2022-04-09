package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Alias;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;

/**
 * @author Luca W. | GPS_Gamer
 **/

@Name("broadcast")
@Alias("bc")
@Description("Broadcasts a message to all players.")
@Permission("jukebox.command.broadcast")
public class BroadcastCommand extends Command {

    @Override
    public void execute( CommandSender sender, String command, String[] args ) {
        if ( args.length == 0 ) {
            sender.sendMessage( "§cUsage: /jukebox broadcast <message>" );
            return;
        }

        StringBuilder broadcastMessage = new StringBuilder();
        for ( String arg : args ) {
            broadcastMessage.append(arg).append( " " );
        }

        sender.getServer().broadcastMessage( "[Jukebox] " + broadcastMessage);
    }
}
