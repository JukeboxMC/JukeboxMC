package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentLure : JukeboxEnchantment() {

    override fun getId(): Int {
        return 24
    }

    override fun getMaxLevel(): Int {
        return 3
    }

    override fun getMinCost(level: Int): Int {
        return level + 8 * level + 6
    }

    override fun getMaxCost(level: Int): Int {
        return this.getMinCost(level) + 45 + level
    }

    override fun getRarity(): Rarity {
        return Rarity.RARE
    }
}
