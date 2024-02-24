package org.jukeboxmc.server.command.internal

import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.ParameterType
import org.jukeboxmc.api.command.annotation.*
import org.jukeboxmc.server.JukeboxServer

@Name("kick")
@Description("Kick a player with reason.")
@Permission("jukeboxmc.command.kick")
@Parameters(parameter = [
    Parameter("player", ParameterType.TARGET, optional = false),
    Parameter("reason", ParameterType.STRING, optional = true)
])
class KickCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        if (args.size == 1 || args.size >= 2) {
            val target = JukeboxServer.getInstance().getPlayer(args[0])
            if (target != null) {
                val reason = if (args.size >= 2)  this.createString(args, 1) else "Kicked by admin."
                target.kick(reason)
            } else {
                commandSender.sendMessage("Target not found.")
            }
        } else {
            commandSender.sendMessage("Target not found.")
        }
    }

    private fun createString(args: Array<String>, start: Int): String {
        return createString(args, start, " ")
    }

    private fun createString(args: Array<String>, start: Int, glue: String): String {
        val string = StringBuilder()
        for (x in start until args.size) {
            string.append(args[x])
            if (x != args.size - 1) {
                string.append(glue)
            }
        }
        return string.toString()
    }
}