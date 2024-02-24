package org.jukeboxmc.server.command.internal

import org.apache.commons.lang3.math.NumberUtils
import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.ParameterType
import org.jukeboxmc.api.command.annotation.*
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.world.gamerule.GameRule

@Name("gamerule")
@Description("Set a gamerule value")
@Permission("jukeboxmc.command.gamerule")
@Parameters(parameter = [
    Parameter("gamerule", enumValues = arrayOf(
            "commandblocksenabled", "commandblockoutput", "dodaylightcycle",
            "doentitydrops", "dofiretick", "doinsomnia", "doimmediaterespawn", "domobloot", "domobspawning",
            "dotiledrops", "doweathercycle", "drowningdamage", "falldamage", "firedamage", "keepinventory",
            "maxcommandchainlength", "mobgriefing", "naturalregeneration", "pvp", "randomtickspeed", "sendcommandfeedback",
            "showcoordinates", "showdeathmessages", "spawnradius", "tntexplodes", "showtags", "freezedamage",
            "respawnblocksexplode", "showbordereffect", "functioncommandlimit", "recipesunlock", "dolimitedcrafting",
            "playerssleepingpercentage",
    ), optional = false),
    Parameter("value", enumValues = arrayOf("true", "false"), optional = false)
])
@Parameters(parameter = [
    Parameter("gamerule", enumValues = arrayOf(
            "commandblocksenabled", "commandblockoutput", "dodaylightcycle",
            "doentitydrops", "dofiretick", "doinsomnia", "doimmediaterespawn", "domobloot", "domobspawning",
            "dotiledrops", "doweathercycle", "drowningdamage", "falldamage", "firedamage", "keepinventory",
            "maxcommandchainlength", "mobgriefing", "naturalregeneration", "pvp", "randomtickspeed", "sendcommandfeedback",
            "showcoordinates", "showdeathmessages", "spawnradius", "tntexplodes", "showtags", "freezedamage",
            "respawnblocksexplode", "showbordereffect", "functioncommandlimit", "recipesunlock", "dolimitedcrafting",
            "playerssleepingpercentage",
    ), optional = false),
    Parameter("value", ParameterType.INT, optional = false)
])
class GameRuleCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        if (commandSender is Player) {
            if (args.size == 1) {
                val gameRuleValue = args[0]
                val gameRule = GameRule.fromIdentifier(gameRuleValue)
                if (gameRule != null) {
                    if (gameRule.type == GameRule.Type.INT) {
                        val value = commandSender.getWorld().getGameRule<Int>(gameRule)
                        commandSender.sendMessage(gameRule.identifier + " = " + value)
                    } else {
                        val value = commandSender.getWorld().getGameRule<Boolean>(gameRule)
                        commandSender.sendMessage(gameRule.identifier + " = " + value)
                    }
                } else {
                    commandSender.sendMessage("§cGameRule not found.")
                }
            } else if (args.size == 2) {
                val gameRuleValue = args[0]
                val gameRule = GameRule.fromIdentifier(gameRuleValue)
                if (gameRule != null) {
                    if (gameRule.type == GameRule.Type.INT) {
                        if (NumberUtils.isCreatable(args[1])) {
                            val value = args[1].toInt()
                            commandSender.getWorld().setGameRule(gameRule, value)
                            commandSender.sendMessage("Gamerule " + gameRule.identifier + " has been updated to " + value)
                        } else {
                            commandSender.sendMessage("§cValue must be a integer.")
                        }
                    } else {
                        val boolValue = args[1]
                        if (boolValue.equals("true", ignoreCase = true) || boolValue.equals("false", ignoreCase = true)) {
                            val value = boolValue.toBoolean()
                            commandSender.getWorld().setGameRule(gameRule, value)
                            commandSender.sendMessage("Gamerule " + gameRule.identifier + " has been updated to " + value)
                        } else {
                            commandSender.sendMessage("§cValue must be a boolean.")
                        }
                    }
                } else {
                    commandSender.sendMessage("§cGameRule not found.")
                }
            }
        } else {
            commandSender.sendMessage("§cYou must be a player.")
        }
    }
}