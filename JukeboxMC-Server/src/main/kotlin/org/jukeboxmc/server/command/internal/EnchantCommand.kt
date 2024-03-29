package org.jukeboxmc.server.command.internal

import org.apache.commons.lang3.EnumUtils
import org.apache.commons.lang3.math.NumberUtils
import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.ParameterType
import org.jukeboxmc.api.command.annotation.*
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.server.JukeboxServer
import java.util.*

@Name("enchant")
@Description("Adds an enchantment to a player's selected item.")
@Permission("jukeboxmc.command.enchant")
@Parameters(
    parameter = [
        Parameter("player", ParameterType.TARGET, optional = false),
        Parameter(
            "enchantment", enumValues = arrayOf(
                "aqua_affinity", "bane_of_arthropods",
                "blast_protection", "channeling", "curse_of_binding", "curse_of_vanishing",
                "depth_strider", "efficiency", "feather_falling", "fire_aspect", "fire_protection",
                "flame", "fortune", "impaling", "infinity", "knockback", "looting", "loyalty",
                "luck_of_the_sea", "lure", "mending", "multishot", "piercing", "power", "projectile_protection",
                "protection", "punch", "quick_charge", "respiration", "riptide", "sharpness", "silk_touch",
                "smite", "soul_speed", "thorns", "unbreaking", "swift_sneak"
            ), optional = false
        ),
        Parameter("level", ParameterType.INT, optional = false)
    ]
)
class EnchantCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        if (commandSender is Player) {
            if (args.size >= 2) {
                val target = JukeboxServer.getInstance().getPlayer(args[0])
                if (target == null) {
                    commandSender.sendMessage("§cThe player " + args[0] + " could not be found")
                    return
                }
                val enchantmentValue = args[1].lowercase(Locale.getDefault())
                if (!EnumUtils.isValidEnum(EnchantmentType::class.java, enchantmentValue.uppercase())) {
                    commandSender.sendMessage("§cEnchantment not found.")
                    return
                }
                val enchantmentType = EnchantmentType.valueOf(enchantmentValue.uppercase())
                var level = 1
                if (args.size >= 3) {
                    if (!NumberUtils.isCreatable(args[2])) {
                        commandSender.sendMessage("§cThe level must be a number.")
                        return
                    }
                    level = args[2].toInt()
                }
                val itemInHand = target.getInventory().getItemInHand()
                if (itemInHand.getType() == ItemType.AIR) {
                    commandSender.sendMessage("§cAir can not be enchanted.")
                    return
                }
                val enchantment = itemInHand.getEnchantment(enchantmentType)
                if (enchantment != null) {
                    commandSender.sendMessage("§cThe item already has the enchantment")
                    return
                }
                itemInHand.addEnchantment(enchantmentType, level)
                target.getInventory().setItemInHand(itemInHand)
                commandSender.sendMessage("Enchanting succeeded for " + target.getName())
            }
        } else {
            commandSender.sendMessage("§cYou must be a player.")
        }
    }
}