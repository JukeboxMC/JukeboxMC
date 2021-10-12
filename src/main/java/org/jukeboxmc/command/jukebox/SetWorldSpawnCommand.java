package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "setworldspawn" )
@Description ( "Set a new world spawn" )
@Permission ( "jukeboxmc.command.setworldspawn" )
public class SetWorldSpawnCommand extends Command {

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( commandSender instanceof Player ) {
            Player player = (Player) commandSender;
            World world = player.getWorld();
            world.setSpawnLocation( player.getLocation() );
            player.sendMessage( "You set the new world spawn at [X=" + player.getBlockX() + " Y=" + player.getBlockY() + " Z=" + player.getBlockZ() + "]" );
        } else {
            commandSender.sendMessage( "§cYou must be a player" );
        }
    }
}
