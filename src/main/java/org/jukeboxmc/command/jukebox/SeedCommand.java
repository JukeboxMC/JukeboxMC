package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandData;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name("seed")
@Description("Show the current world seed.")
@Permission("jukeboxmc.command.seed")
public class SeedCommand extends Command {

    public SeedCommand() {
        super( CommandData.builder()
                .build() );
    }

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( commandSender instanceof Player player ) {
            player.sendMessage( "§7Seed§8: §e" + player.getWorld().getSeed() );
        } else {
            commandSender.sendMessage( "§cYou must be a player." );
        }
    }
}
