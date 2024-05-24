package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentDepthStrider : JukeboxEnchantment() {

    override fun getId(): Int {
        return 7
    }

    override fun getMaxLevel(): Int {
        return 3
    }

    override fun getMinCost(level: Int): Int {
        return level * 10
    }

    override fun getMaxCost(level: Int): Int {
        return this.getMinCost(level) + 15
    }

    override fun getRarity(): Rarity {
        return Rarity.RARE
    }
}
