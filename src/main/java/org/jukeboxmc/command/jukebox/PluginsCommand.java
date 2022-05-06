package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandData;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.plugin.Plugin;

import java.util.Collection;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "plugins" )
@Description ( "Show all enabled plugins." )
@Permission ( "org.jukeboxmc.command.plugins" )
public class PluginsCommand extends Command {
    public PluginsCommand() {
        super( CommandData.builder()
                .addAlias( "pl" )
                .build() );
    }

    @Override
    public void execute( CommandSender commandSender, String command, String[] args ) {
        Collection<Plugin> plugins = Server.getInstance().getPluginManager().getPlugins();

        if ( plugins.isEmpty() ) {
            commandSender.sendMessage( "No plugins loaded." );
            return;
        }

        StringBuilder stringBuilder = new StringBuilder( "Plugins (" + plugins.size() + "§r): " );

        for ( Plugin plugin : plugins ) {
            stringBuilder.append( plugin.isEnabled() ? "§a" : "§c" ).append( plugin.getName() ).append( " v" ).append( plugin.getVersion() ).append( "§7, " );
        }

        stringBuilder.setLength( stringBuilder.length() - 4 );

        commandSender.sendMessage( stringBuilder.toString() );
    }
}
