package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentFireAspect : JukeboxEnchantment() {

    override fun getId(): Int {
        return 13
    }

    override fun getMaxLevel(): Int {
        return 2
    }

    override fun getMinCost(level: Int): Int {
        return 10 + (level - 1) * 20
    }

    override fun getMaxCost(level: Int): Int {
        return super.getMaxCost(level) + 50
    }

    override fun getRarity(): Rarity {
        return Rarity.RARE
    }
}
