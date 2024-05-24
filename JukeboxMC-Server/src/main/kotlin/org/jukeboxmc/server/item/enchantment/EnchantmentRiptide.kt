package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Enchantment
import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentRiptide : JukeboxEnchantment() {

    override fun getId(): Int {
        return 30
    }

    override fun getMaxLevel(): Int {
        return 3
    }

    override fun getMinCost(level: Int): Int {
        return 7 * level + 10
    }

    override fun getRarity(): Rarity {
        return Rarity.RARE
    }

    override fun checkCompatibility(enchantment: Enchantment): Boolean {
        return super.checkCompatibility(enchantment) && enchantment !is EnchantmentLoyalty && enchantment !is EnchantmentChanneling
    }

}
