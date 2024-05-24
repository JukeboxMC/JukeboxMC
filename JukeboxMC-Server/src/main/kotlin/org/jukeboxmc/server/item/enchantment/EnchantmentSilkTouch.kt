package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Enchantment
import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentSilkTouch : JukeboxEnchantment() {

    override fun getId(): Int {
        return 16
    }

    override fun getMaxLevel(): Int {
        return 1
    }

    override fun getRarity(): Rarity {
        return Rarity.VERY_RARE
    }

    override fun getMinCost(level: Int): Int {
        return 15
    }

    override fun getMaxCost(level: Int): Int {
        return super.getMinCost(level) + 50
    }

    override fun checkCompatibility(enchantment: Enchantment): Boolean {
        return super.checkCompatibility(enchantment) && enchantment !is EnchantmentFortune
    }

}
