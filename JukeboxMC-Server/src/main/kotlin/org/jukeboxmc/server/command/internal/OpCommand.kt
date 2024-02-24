package org.jukeboxmc.server.command.internal

import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.ParameterType
import org.jukeboxmc.api.command.annotation.*
import org.jukeboxmc.server.JukeboxServer

@Name("op")
@Description("Add a player to operator")
@Permission("jukeboxmc.command.op")
@Parameters(parameter = [
    Parameter("player", ParameterType.TARGET, optional = false)
])
class OpCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        if (args.size == 1) {
            val playerName = args[0]

            if (playerName.isNotEmpty()) {
                val target = JukeboxServer.getInstance().getPlayer(playerName)
                if (target != null) {
                    if (target.isOperator()) {
                        commandSender.sendMessage("The player $playerName is already an operator")
                        return
                    }
                    target.setOperator(true)
                    target.sendMessage("You are now operator")
                } else {
                    if (!JukeboxServer.getInstance().getOperators().isOperator(playerName)) {
                        JukeboxServer.getInstance().getOperators().add(playerName)
                    } else {
                        commandSender.sendMessage("The player $playerName is already an operator")
                    }
                }
                commandSender.sendMessage("The player $playerName is now an operator")
            } else {
                commandSender.sendMessage("§cYou must specify a name")
            }
        }
    }
}