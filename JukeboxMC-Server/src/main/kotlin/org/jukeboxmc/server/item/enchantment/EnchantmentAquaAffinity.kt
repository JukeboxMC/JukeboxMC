package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentAquaAffinity : JukeboxEnchantment() {

    override fun getId(): Int {
        return 8
    }

    override fun getMaxLevel(): Int {
        return 1
    }

    override fun getMinCost(level: Int): Int {
        return 1
    }

    override fun getMaxCost(level: Int): Int {
        return getMinCost(level) + 40
    }

    override fun getRarity(): Rarity {
        return Rarity.RARE
    }

}
