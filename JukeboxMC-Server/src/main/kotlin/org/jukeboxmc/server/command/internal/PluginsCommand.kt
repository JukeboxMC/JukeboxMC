package org.jukeboxmc.server.command.internal

import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.annotation.Description
import org.jukeboxmc.api.command.annotation.Name
import org.jukeboxmc.api.command.annotation.Permission
import org.jukeboxmc.server.JukeboxServer

@Name("plugins")
@Description("Show all enabled plugins.")
@Permission("jukeboxmc.command.plugins")
class PluginsCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        val plugins = JukeboxServer.getInstance().getPluginManager().getPlugins()
        if (plugins.isEmpty()) {
            commandSender.sendMessage("No plugins loaded.")
            return
        }
        val stringBuilder = StringBuilder("Plugins (" + plugins.size + "§r): ")
        for (plugin in plugins) {
            stringBuilder.append(if (plugin.isEnabled()) "§a" else "§c").append(plugin.getName()).append(" v").append(plugin.getVersion()).append("§7, ")
        }
        stringBuilder.setLength(stringBuilder.length - 4)
        commandSender.sendMessage(stringBuilder.toString())
    }
}