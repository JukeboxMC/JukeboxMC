package org.jukeboxmc.command.internal;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandData;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.util.Utils;

import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "status" )
@Description ( "Show the current status form the server." )
public class StatusCommand extends Command {

    public StatusCommand() {
        super( CommandData.builder().build() );
    }

    @Override
    public void execute(@NotNull CommandSender commandSender, String command, String[] args ) {
        StringBuilder builder = new StringBuilder();
        builder.append( "§7======== §8| §bServer Status §8| §7========" ).append( "\n" );
        builder.append( "§7Uptime§8: §b" ).append( this.formatUptime( System.currentTimeMillis() - Server.getInstance().getStartTime() ) ).append( "\n" );

        long currentTps = Server.getInstance().getCurrentTps();
        String tpsColor = currentTps < 17 ? "§e" : currentTps < 12 ? "§c" : "§a";
        builder.append( "§7Current TPS§8: " ).append( tpsColor ).append( currentTps ).append( "\n" );

        Runtime runtime = Runtime.getRuntime();
        double totalMB = Utils.round( ( (double) runtime.totalMemory() ) / 1024 / 1024, 2 );
        double usedMB = Utils.round( (double) ( runtime.totalMemory() - runtime.freeMemory() ) / 1024 / 1024, 2 );
        double maxMB = Utils.round( ( (double) runtime.maxMemory() ) / 1024 / 1024, 2 );
        double usage = usedMB / maxMB * 100;

        builder.append( "§7Used VM Memory§8: §b" ).append( usedMB ).append( " MB. (" ).append( Utils.round( usage, 2 ) ).append( "%)" ).append( "\n" );
        builder.append( "§7Total VM Memory§8: §b" ).append( totalMB ).append( " MB." );
        commandSender.sendMessage( builder.toString() );
    }

    private @NotNull String formatUptime(long uptime ) {
        long days = TimeUnit.MILLISECONDS.toDays( uptime );
        uptime -= TimeUnit.DAYS.toMillis( days );
        long hours = TimeUnit.MILLISECONDS.toHours( uptime );
        uptime -= TimeUnit.HOURS.toMillis( hours );
        long minutes = TimeUnit.MILLISECONDS.toMinutes( uptime );
        uptime -= TimeUnit.MINUTES.toMillis( minutes );
        long seconds = TimeUnit.MILLISECONDS.toSeconds( uptime );
        return days + " §7Days §b" + hours + " §7Hours §b" + minutes + " §7Minutes §b" + seconds + " §7Seconds";
    }
}
