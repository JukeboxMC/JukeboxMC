package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentPunch : JukeboxEnchantment() {

    override fun getId(): Int {
        return 20
    }

    override fun getMaxLevel(): Int {
        return 2
    }

    override fun getMinCost(level: Int): Int {
        return 12 + (level - 1) * 20
    }

    override fun getMaxCost(level: Int): Int {
        return this.getMinCost(level) + 25
    }

    override fun getRarity(): Rarity {
        return Rarity.RARE
    }

}
