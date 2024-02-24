package org.jukeboxmc.server.command.internal

import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.ParameterType
import org.jukeboxmc.api.command.annotation.*
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.world.Dimension
import org.jukeboxmc.server.JukeboxServer

@Name("teleport")
@Alias("tp")
@Description("Teleport to another location or player.")
@Permission("jukeboxmc.command.teleport")
@Parameters(parameter = [
    Parameter("player", ParameterType.TARGET, optional = false)
])
@Parameters(parameter = [
    Parameter("position", ParameterType.POSITION, optional = false),
    Parameter("dimension", enumValues = arrayOf("overworld", "nether", "the_end"), optional = true)
])
@Parameters(parameter = [
    Parameter("player", ParameterType.TARGET, optional = false),
    Parameter("target", ParameterType.TARGET, optional = false),
])
class TeleportCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        if (args.size == 1) {
            if (commandSender is Player) {
                val targetName = args[0]
                if (targetName.isEmpty()) {
                    commandSender.sendMessage("§cYou must specify a name")
                    return
                }

                val target = JukeboxServer.getInstance().getPlayer(targetName)
                if (target == null) {
                    commandSender.sendMessage("§cThe player $targetName could not be found")
                    return
                }
                commandSender.teleport(target)
                commandSender.sendMessage("You have been teleported to $targetName")
            } else {
                commandSender.sendMessage("§cYou must be a player")
            }
        } else if (args.size == 2) {
            val playerName = args[0]
            val targetName = args[1]

            if (playerName.isEmpty() || targetName.isEmpty()) {
                commandSender.sendMessage("§cYou must specify a name for both players")
                return
            }

            val tagetPlayer = JukeboxServer.getInstance().getPlayer(playerName)
            val toPlayer = JukeboxServer.getInstance().getPlayer(targetName)

            if (tagetPlayer == null) {
                commandSender.sendMessage("§cPlayer $playerName could not be found")
                return
            }

            if (toPlayer == null) {
                commandSender.sendMessage("§cPlayer $targetName could not be found")
                return
            }

            tagetPlayer.teleport(toPlayer)
            commandSender.sendMessage("The player $playerName has been teleported to $targetName")
        } else if (args.size == 3 || args.size == 4) {
            if (commandSender is Player) {
                val number1 = args[0]
                val number2 = args[1]
                val number3 = args[2]
                var dimension: String? = null
                if (args.size == 4) {
                    dimension = args[3]
                }
                if (number1.isEmpty() || number2.isEmpty() || number3.isEmpty()) {
                    commandSender.sendMessage("§cYou must specify a position")
                    return
                }
                try {
                    val x = number1.toInt()
                    val y = number2.toInt()
                    val z = number3.toInt()
                    commandSender.teleport(Location(commandSender.getWorld(), Vector(x, y, z), if (dimension == null) commandSender.getDimension() else Dimension.valueOf(dimension.uppercase())))
                    commandSender.sendMessage("You have benn teleported to $x, $y, $z")
                } catch (e: NumberFormatException) {
                    commandSender.sendMessage("§cYou must specify a number")
                }
            } else {
                commandSender.sendMessage("§cYou must be a player")
            }
        }
    }
}