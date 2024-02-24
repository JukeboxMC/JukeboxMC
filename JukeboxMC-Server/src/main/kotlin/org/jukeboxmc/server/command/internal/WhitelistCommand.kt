package org.jukeboxmc.server.command.internal

import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.ParameterType
import org.jukeboxmc.api.command.annotation.*
import org.jukeboxmc.server.JukeboxServer

@Name("whitelist")
@Description("Manages the list of players allowed to use this server")
@Permission("jukeboxmc.command.whitelist")
@Parameters(parameter = [
    Parameter("whitelist", enumValues = arrayOf("on", "off"), optional = false)
])
@Parameters(parameter = [
    Parameter("list", enumValues = arrayOf("list"), optional = false)
])
@Parameters(parameter = [
    Parameter("action", enumValues = arrayOf("add", "remove"), optional = false),
    Parameter("player", ParameterType.TARGET , optional = false)
])
class WhitelistCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        if (args.size == 1) {
            if (args[0].equals("on", ignoreCase = true)) {
                if (JukeboxServer.getInstance().isWhitelist()) {
                    commandSender.sendMessage("§cThe whitelist is already activated")
                    return
                }
                JukeboxServer.getInstance().setWhitelist(true)
                commandSender.sendMessage("The whitelist was activated")
            } else if (args[0].equals("off", ignoreCase = true)) {
                if (!JukeboxServer.getInstance().isWhitelist()) {
                    commandSender.sendMessage("§cThe whitelist is already disabled")
                    return
                }
                JukeboxServer.getInstance().setWhitelist(false)
                commandSender.sendMessage("The whitelist was deactivated")
            } else if (args[0].equals("list", ignoreCase = true)) {
                val builder = StringBuilder()
                for (playerName in JukeboxServer.getInstance().getWhitelist().getPlayers()) {
                    builder.append(playerName).append(", ")
                }
                builder.setLength(builder.length - 2)
                commandSender.sendMessage("The following players are on the whitelist: $builder")
            } else {
                this.sendUsage(commandSender)
            }
        } else if (args.size == 2) {
            if (args[0].equals("add", ignoreCase = true)) {
                val playerName = args[1]
                if (JukeboxServer.getInstance().getWhitelist().isWhitelisted(playerName)) {
                    commandSender.sendMessage("§c$playerName is already on the whitelist")
                    return
                }
                JukeboxServer.getInstance().getWhitelist().add(playerName)
                commandSender.sendMessage("$playerName has been added to the whitelist")
            } else if (args[0].equals("remove", ignoreCase = true)) {
                val playerName = args[1]
                if (!JukeboxServer.getInstance().getWhitelist().isWhitelisted(playerName)) {
                    commandSender.sendMessage("§c$playerName is not on the whitelist")
                    return
                }
                JukeboxServer.getInstance().getWhitelist().remove(playerName)
                commandSender.sendMessage("$playerName was removed from the whitelist")
            } else {
                this.sendUsage(commandSender)
            }
        } else {
            this.sendUsage(commandSender)
        }
    }

    private fun sendUsage(commandSender: CommandSender) {
        commandSender.sendMessage("Usage:")
        commandSender.sendMessage("- /whitelist <add|remove> <player>")
        commandSender.sendMessage("- /whitelist <on|off>")
    }
}