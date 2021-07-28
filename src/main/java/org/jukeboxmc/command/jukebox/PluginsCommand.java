package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandOutput;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Alias;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.plugin.Plugin;

import java.util.Collection;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Name ( "plugins" )
@Alias ( "pl" )
@Description ( "Show all enabled plugins." )
@Permission ( "jukebox.command.plugins" )
public class PluginsCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender commandSender, String command, Map<String, Object> arguments ) {
        Collection<Plugin> plugins = Server.getInstance().getPluginManager().getPlugins();

        if ( plugins.isEmpty() ) {
            return new CommandOutput().fail( "No plugins loaded." );
        }

        StringBuilder stringBuilder = new StringBuilder( "Plugins (" + plugins.size() + "§r): " );

        for ( Plugin plugin : plugins ) {
            stringBuilder.append( plugin.isEnabled() ? "§a" : "§c" ).append( plugin.getName() ).append( " v" ).append( plugin.getVersion() ).append( "§7, " );
        }

        stringBuilder.setLength( stringBuilder.length() - 4 );

        return new CommandOutput().success( stringBuilder.toString() );
    }
}
