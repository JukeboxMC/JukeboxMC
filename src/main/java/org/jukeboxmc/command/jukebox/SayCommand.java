package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.console.ConsoleSender;
import org.jukeboxmc.player.Player;

/**
 * @author Luca W. | GPS_Gamer
 **/

@Name("say")
@Description("Send a message to all online players.")
@Permission("jukebox.command.say")
public class SayCommand extends Command {

    @Override
    public void execute( CommandSender sender, String command, String[] args ) {
        if ( args.length == 0 ) {
            sender.sendMessage( "§cUsage: /jukebox broadcast <message>" );
            return;
        }

       String senderString;
       if ( sender instanceof Player ) {
           senderString = ((Player) sender).getName();
       } else if ( sender instanceof ConsoleSender ) {
           senderString = "Server";
       } else {
           senderString = "Unknown";
       }

       StringBuilder sayMessage = new StringBuilder();
       for ( String arg : args ) {
           sayMessage.append(arg).append(" ");
       }
       if ( sayMessage.length() > 0 ) {
           sayMessage.substring(0, sayMessage.length() - 1);
       }

       sender.getServer().broadcastMessage( "[" + senderString + "] " + sayMessage );
    }
}
