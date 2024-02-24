package org.jukeboxmc.server.command.internal

import org.apache.commons.lang3.math.NumberUtils
import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.ParameterType
import org.jukeboxmc.api.command.annotation.*
import org.jukeboxmc.api.player.Player

@Name("time")
@Description("Change the world game time")
@Permission("jukeboxmc.command.time")
@Parameters(
    parameter = [
        Parameter("set", enumValues = arrayOf("set"), optional = false),
        Parameter(
            "time",
            enumValues = arrayOf("sunrise", "day", "noon", "sunset", "night", "midnight"),
            optional = false
        )
    ]
)
@Parameters(
    parameter = [
        Parameter("set", enumValues = arrayOf("set"), optional = false),
        Parameter("time", ParameterType.INT, optional = false)
    ]
)
class TimeCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        if (commandSender !is Player) {
            commandSender.sendMessage("§cYou must be a player")
            return
        }

        if (args.size == 2) {
            if (NumberUtils.isCreatable(args[1])) {
                val time = args[1].toInt()
                commandSender.getWorld().setTime(time)
                commandSender.sendMessage("Set the time to $time")
            } else {
                when (args[1]) {
                    "sunrise" -> {
                        commandSender.getWorld().setTime(23000)
                        commandSender.sendMessage("Set the time to 23000")
                    }

                    "day" -> {
                        commandSender.getWorld().setTime(1000)
                        commandSender.sendMessage("Set the time to 1000")
                    }

                    "noon" -> {
                        commandSender.getWorld().setTime(6000)
                        commandSender.sendMessage("Set the time to 6000")
                    }

                    "sunset" -> {
                        commandSender.getWorld().setTime(12000)
                        commandSender.sendMessage("Set the time to 12000")
                    }

                    "night" -> {
                        commandSender.getWorld().setTime(13000)
                        commandSender.sendMessage("Set the time to 13000")
                    }

                    "midnight" -> {
                        commandSender.getWorld().setTime(18000)
                        commandSender.sendMessage("Set the time to 18000")
                    }

                    else -> {}
                }
            }
        }
    }
}