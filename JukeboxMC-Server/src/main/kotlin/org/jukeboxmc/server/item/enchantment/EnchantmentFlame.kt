package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentFlame : JukeboxEnchantment() {

    override fun getId(): Int {
        return 21
    }

    override fun getMaxLevel(): Int {
        return 1
    }

    override fun getMinCost(level: Int): Int {
        return 20
    }

    override fun getMaxCost(level: Int): Int {
        return 50
    }

    override fun getRarity(): Rarity {
        return Rarity.RARE
    }
}
