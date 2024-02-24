package org.jukeboxmc.server.command.internal

import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.ParameterType
import org.jukeboxmc.api.command.annotation.*
import org.jukeboxmc.server.JukeboxServer

@Name("deop")
@Description("Remove player from operator")
@Permission("jukeboxmc.command.deop")
@Parameters(parameter = [
    Parameter("player", ParameterType.TARGET, optional = false)
])
class DeopCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        if (args.size == 1) {
            val playerName = args[0]
            if (playerName.isNotEmpty()) {
                val target = JukeboxServer.getInstance().getPlayer(playerName)
                if (target != null) {
                    if (!target.isOperator()) {
                        commandSender.sendMessage("The player $playerName is not a operator")
                        return
                    }
                    target.setOperator(false)
                    target.sendMessage("You are no longer a operator")
                } else {
                    if (JukeboxServer.getInstance().getOperators().isOperator(playerName)) {
                        JukeboxServer.getInstance().getOperators().remove(playerName)
                    } else {
                        commandSender.sendMessage("The player $playerName is not a operator")
                    }
                }
                commandSender.sendMessage("The player $playerName is no longer a operator")
            } else {
                commandSender.sendMessage("§cYou must specify a name")
            }
        }
    }
}