package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentInfinity : JukeboxEnchantment() {

    override fun getId(): Int {
        return 22
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
        return Rarity.VERY_RARE
    }

}
