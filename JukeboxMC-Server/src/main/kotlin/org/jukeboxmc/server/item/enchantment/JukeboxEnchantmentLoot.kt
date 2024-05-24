package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Enchantment

abstract class JukeboxEnchantmentLoot : JukeboxEnchantment() {

    override fun getMinCost(level: Int): Int {
        return 15 + (level - 1) * 9
    }

    override fun getMaxCost(level: Int): Int {
        return this.getMinCost(level) + 45 + level
    }

    override fun checkCompatibility(enchantment: Enchantment): Boolean {
        return super.checkCompatibility(enchantment) && enchantment !is EnchantmentSilkTouch
    }
}