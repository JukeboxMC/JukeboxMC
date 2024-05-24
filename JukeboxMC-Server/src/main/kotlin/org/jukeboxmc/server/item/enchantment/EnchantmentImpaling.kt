package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentImpaling : JukeboxEnchantment() {

    override fun getId(): Int {
        return 29
    }

    override fun getMaxLevel(): Int {
        return 5
    }

    override fun getMinCost(level: Int): Int {
        return 8 * level - 7
    }

    override fun getMaxCost(level: Int): Int {
        return this.getMinCost(level) + 20
    }

    override fun getRarity(): Rarity {
        return Rarity.RARE
    }

}
