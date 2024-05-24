package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentLoyalty : JukeboxEnchantmentTrident() {

    override fun getId(): Int {
        return 31
    }

    override fun getMaxLevel(): Int {
        return 3
    }

    override fun getMinCost(level: Int): Int {
        return 7 * level + 5
    }

    override fun getRarity(): Rarity {
        return Rarity.UNCOMMON
    }

}
