package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "status" )
@Description ( "Show the current server status" )
@Permission ( "jukeboxmc.command.status" )
public class StatusCommand extends Command {

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        Server server = Server.getInstance();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "§7======== §8| §eJukeboxMC Status §8| §7========" ).append( "\n" );
        stringBuilder.append( "§7TPS§8: §e" ).append( Math.round( server.getCurrentTps() ) ).append( "\n" );
        stringBuilder.append( "§7CurrentTick§8: §e" ).append( server.getCurrentTick() ).append( "\n" );

        for ( World world : server.getWorlds() ) {
            stringBuilder.append( "§7World§8: §e" ).append( world.getName() ).append( "\n" );
            stringBuilder.append( "§7Chunks§8: §e" ).append( world.getChunks( Dimension.OVERWORLD ).size() ).append( "\n" );
            stringBuilder.append( "§7Entities§8: §e" ).append( world.getEntities().size() ).append( "\n" );
            stringBuilder.append( "§7BlockEntites§8: §e" ).append( world.getBlockEntities( Dimension.OVERWORLD ).size() );
        }
        commandSender.sendMessage( stringBuilder.toString() );
    }
}
