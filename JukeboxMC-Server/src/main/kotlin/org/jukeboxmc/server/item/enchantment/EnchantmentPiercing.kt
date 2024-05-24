package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Enchantment
import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentPiercing : JukeboxEnchantment() {

    override fun getId(): Int {
        return 34
    }

    override fun getMaxLevel(): Int {
        return 4
    }

    override fun getMinCost(level: Int): Int {
        return 1 + 10 * (level - 1)
    }

    override fun getRarity(): Rarity {
        return Rarity.COMMON
    }

    override fun checkCompatibility(enchantment: Enchantment): Boolean {
        return super.checkCompatibility(enchantment) && enchantment !is EnchantmentMultiShot
    }

}
