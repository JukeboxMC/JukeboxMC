package org.jukeboxmc.server.command.internal

import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.ParameterType
import org.jukeboxmc.api.command.annotation.*
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.server.JukeboxServer

@Alias("gm")
@Name("gamemode")
@Description("Sets a player's game mode.")
@Permission("jukeboxmc.command.gamemode")
@Parameters(parameter = [
    Parameter("gamemode", ParameterType.INT),
    Parameter("player", ParameterType.TARGET, optional = true)
])
@Parameters(parameter = [
    Parameter("gamemode", enumValues = arrayOf("survival", "creative", "adventure", "spectator"), optional = false),
    Parameter("player", ParameterType.TARGET, optional = true)
])
class GameModeCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        if (args.size == 1) {
            if (commandSender is Player) {
                val gamemodeName = args[0]
                if (gamemodeName.isEmpty()) {
                    commandSender.sendMessage("§cYou must specify a gamemode")
                    return
                }
                val gameMode = getGameModeByName(gamemodeName)
                if (gameMode == null) {
                    commandSender.sendMessage("§cGamemode $gamemodeName not found.")
                    return
                }
                commandSender.setGameMode(gameMode)
                commandSender.sendMessage("Your game mode has been updated to " + gameMode.identifier)
            } else {
                commandSender.sendMessage("§cYou must be a player")
            }
        } else if (args.size == 2){
            val gamemodeName = args[0]
            val targetName = args[1]
            if (gamemodeName.isEmpty()) {
                commandSender.sendMessage("§cYou must specify a gamemode")
                return
            }
            if (targetName.isEmpty()) {
                commandSender.sendMessage("§cYou must specify a player")
                return
            }
            val target = JukeboxServer.getInstance().getPlayer(targetName)
            if (target == null) {
                commandSender.sendMessage("Player $targetName not be found")
                return
            }
            val gameMode = getGameModeByName(gamemodeName)
            if (gameMode == null) {
                commandSender.sendMessage("§cGamemode $gamemodeName not found.")
                return
            }
            target.setGameMode(gameMode)
            target.sendMessage("Your game mode has been updated to " + gameMode.identifier)
            if (target === commandSender) {
                commandSender.sendMessage("Set own game mode to " + gameMode.identifier)
            } else {
                commandSender.sendMessage("Set " + target.getName() + "'s game mode to " + gameMode.identifier)
            }
        } else {
            commandSender.sendMessage("§cUsage: /gamemode <gamemode> [target]")
        }
    }

    private fun getGameModeByName(value: String): GameMode? {
        var gameMode: GameMode? = null
        when (value) {
            "survival", "0" -> gameMode = GameMode.SURVIVAL
            "creative", "1" -> gameMode = GameMode.CREATIVE
            "adventure", "2" -> gameMode = GameMode.ADVENTURE
            "spectator", "3" -> gameMode = GameMode.SPECTATOR
        }
        return gameMode
    }
}