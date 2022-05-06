package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandData;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name("stop")
@Description("Stop the server")
@Permission("org.jukeboxmc.command.stop")
public class StopCommand extends Command {

    public StopCommand() {
        super( CommandData.builder().build() );
    }

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        commandSender.sendMessage( "Stopping the server..." );
        commandSender.getServer().shutdown();
    }
}
