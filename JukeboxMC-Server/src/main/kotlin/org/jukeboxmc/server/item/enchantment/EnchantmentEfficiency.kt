package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentEfficiency : JukeboxEnchantment() {

    override fun getId(): Int {
        return 15
    }

    override fun getMaxLevel(): Int {
        return 5
    }

    override fun getMinCost(level: Int): Int {
        return 1 + (level - 1) * 10
    }

    override fun getMaxCost(level: Int): Int {
        return super.getMaxCost(level) + 50
    }

    override fun getRarity(): Rarity {
        return Rarity.COMMON
    }

}
