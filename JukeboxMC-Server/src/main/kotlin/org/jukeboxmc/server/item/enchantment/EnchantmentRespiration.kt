package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentRespiration : JukeboxEnchantment() {

    override fun getId(): Int {
        return 6
    }

    override fun getMaxLevel(): Int {
        return 3
    }

    override fun getMinCost(level: Int): Int {
        return 10 * level
    }

    override fun getMaxCost(level: Int): Int {
        return this.getMinCost(level) + 30
    }

    override fun getRarity(): Rarity {
        return Rarity.RARE
    }
}
