package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Alias;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "worldteleport" )
@Alias ( "worldtp" )
@Description ( "Teleport to another world" )
@Permission ( "jukeboxmc.command.worldteleport" )
public class WorldTeleportCommand extends Command {

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        if ( commandSender instanceof Player ) {
            Player player = (Player) commandSender;

            if ( args.length == 1 ) {
                String worldName = args[0];
                World world = player.getServer().getWorld( worldName );
                if ( world != null ) {
                    player.teleport( world.getSpawnLocation( Dimension.OVERWORLD ) );
                } else {
                    commandSender.sendMessage( "The world \"" + worldName + "\" was not found" );
                }
            }
        } else {
            commandSender.sendMessage( "§cYou must be a player" );
        }
    }
}
