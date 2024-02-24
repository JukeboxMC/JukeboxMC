package org.jukeboxmc.server.command.internal

import org.apache.commons.lang3.EnumUtils
import org.apache.commons.lang3.math.NumberUtils
import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.ParameterType
import org.jukeboxmc.api.command.annotation.*
import org.jukeboxmc.api.effect.Effect
import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.server.JukeboxServer
import java.util.*
import java.util.concurrent.TimeUnit

@Name("effect")
@Description("Add or remove status effect.")
@Permission("jukeboxmc.command.effect")
@Parameters(parameter = [
    Parameter("player", ParameterType.TARGET, optional = false),
    Parameter("effect", enumValues = arrayOf(
            "speed", "slowness", "haste", "mining_fatigue",
            "strength", "instant_health", "instant_damage", "jump_boost", "nausea", "regeneration",
            "resistance", "fire_resistance", "water_breathing", "invisibility", "blindness", "night_vision",
            "hunger", "weakness", "poison", "wither", "health_boost", "absorption", "saturation", "levitation",
            "slow_falling", "conduit_power", "bad_omen", "hero_of_the_village", "darkness", "fatal_poison",
    ), optional = false),
    Parameter("seconds", ParameterType.INT, optional = true),
    Parameter("amplifier", ParameterType.INT, optional = true),
    Parameter("visible", enumValues = arrayOf("true", "false"), optional = true),
])
@Parameters(parameter = [
    Parameter("player", ParameterType.TARGET, optional = false),
    Parameter("clear", enumValues = arrayOf("clear"), optional = false),
])
class EffectCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        if (args.size >= 2) {
            val target = JukeboxServer.getInstance().getPlayer(args[0])
            val type = args[1].lowercase(Locale.getDefault())
            if (target == null) {
                commandSender.sendMessage("§cThe player " + args[0] + " could not be found")
                return
            }
            if (type.equals("clear", ignoreCase = true)) {
                target.removeAllEffects()
                commandSender.sendMessage("Took all effects from " + target.getName())
            } else {
                if (!EnumUtils.isValidEnum(EffectType::class.java, type.uppercase())) {
                    commandSender.sendMessage("Effect not found.")
                    return
                }
                val effectType: EffectType = EffectType.valueOf(type.uppercase())
                if (args.size == 3) {
                    if (!NumberUtils.isCreatable(args[2])) {
                        commandSender.sendMessage("The seconds must be a number.")
                        return
                    }
                }
                val seconds = if (args.size >= 3) args[2].toInt() else 30
                if (args.size == 4) {
                    if (!NumberUtils.isCreatable(args[3])) {
                        commandSender.sendMessage("The amplifier must be a number.")
                        return
                    }
                }
                val amplifier = if (args.size >= 4) args[3].toInt() else 0
                if (args.size >= 5) {
                    if (!args[4].equals("true", ignoreCase = true) && !args[4].equals("false", ignoreCase = true)) {
                        commandSender.sendMessage("The visible must be a boolean.")
                        return
                    }
                }
                val visible = args.size != 5 || args[4].toBoolean()
                val effect = Effect.create(effectType)
                effect.setDuration(seconds, TimeUnit.SECONDS)
                effect.setAmplifier(amplifier)
                effect.setVisible(visible)

                target.addEffect(effect)
                commandSender.sendMessage("Gave " + effectType.name.lowercase() + " * " + amplifier + " to " + target.getName() + " for " + seconds + " seconds.")
            }
        }
    }
}