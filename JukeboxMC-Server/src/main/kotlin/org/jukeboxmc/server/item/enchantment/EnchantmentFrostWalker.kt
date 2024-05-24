package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Enchantment
import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentFrostWalker : JukeboxEnchantment() {

    override fun getId(): Int {
        return 25
    }

    override fun getMaxLevel(): Int {
        return 2
    }

    override fun getRarity(): Rarity {
        return Rarity.VERY_RARE
    }

    override fun getMinCost(level: Int): Int {
        return level * 10
    }

    override fun getMaxCost(level: Int): Int {
        return this.getMinCost(level) + 15
    }

    override fun checkCompatibility(enchantment: Enchantment): Boolean {
        return super.checkCompatibility(enchantment) && enchantment !is EnchantmentDepthStrider
    }
}