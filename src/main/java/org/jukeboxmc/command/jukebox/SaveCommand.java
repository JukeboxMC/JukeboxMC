package org.jukeboxmc.command.jukebox;

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
@Name("save")
@Description("Save the current world.")
@Permission("jukeboxmc.command.save")
public class SaveCommand extends Command {

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( commandSender instanceof Player ) {
            Player player = (Player) commandSender;
            player.sendMessage( "Saving world..." );
            player.getWorld().save();
            player.sendMessage( "World was saved successfully" );
        } else {
            commandSender.sendMessage( "§cYou must be a player" );
        }
    }
}
