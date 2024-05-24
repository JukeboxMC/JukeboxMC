package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentThorns : JukeboxEnchantment() {

    override fun getId(): Int {
        return 5
    }

    override fun getMaxLevel(): Int {
        return 3
    }

    override fun getMinCost(level: Int): Int {
        return 10 + (level - 1) * 20
    }

    override fun getMaxCost(level: Int): Int {
        return super.getMinCost(level) + 50
    }

    override fun getRarity(): Rarity {
        return Rarity.VERY_RARE
    }

}
