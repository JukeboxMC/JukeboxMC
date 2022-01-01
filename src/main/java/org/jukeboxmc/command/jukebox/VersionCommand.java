package org.jukeboxmc.command.jukebox;

import org.jukeboxmc.JukeboxMC;
import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.command.annotation.Alias;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;
import org.jukeboxmc.network.packet.Protocol;
import org.jukeboxmc.plugin.Plugin;

/**
 * @author VazziDE
 * @version 1.0
 */
@Name("version")
@Alias("ver")
@Description("display info about the serversoftware")
@Permission("org.jukeboxmc.command.version")
public class VersionCommand extends Command {

    @Override
    public void execute(CommandSender commandSender, String command, String[] args) {

        if (args.length == 0) {
            sendServerInfo(commandSender);
            return;
        }

        if (Server.getInstance().getPluginManager().isPluginLoaded(args[0])) {
            sendPluginInfo(commandSender, Server.getInstance().getPluginManager().getPluginByName(args[0]));
        }else{
            commandSender.sendMessage("Plugin " + args[0] + " not found.");
        }

    }

    private void sendServerInfo(CommandSender commandSender) {
        commandSender.sendMessage("§fJukeboxMC MCBE Server Software");
        commandSender.sendMessage("§7Software Version: §e" + JukeboxMC.version);
        commandSender.sendMessage("§7Minecraft Version: §e" + Protocol.MINECRAFT_VERSION);
        commandSender.sendMessage("§7Current Protocol: §e" + Protocol.CURRENT_PROTOCOL);
    }

    private void sendPluginInfo(CommandSender commandSender, Plugin plugin) {
        commandSender.sendMessage("§fPlugin Info §7- §a" + plugin.getName());
        commandSender.sendMessage("§7Name: §e" + plugin.getName());
        commandSender.sendMessage("§7Version: §e" + plugin.getVersion());
        commandSender.sendMessage("§7Author: §e" + plugin.getAuthor());
    }

}
