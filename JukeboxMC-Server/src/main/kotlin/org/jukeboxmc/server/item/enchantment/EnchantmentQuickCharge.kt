package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentQuickCharge : JukeboxEnchantment() {

    override fun getId(): Int {
        return 35
    }

    override fun getMaxLevel(): Int {
        return 3
    }

    override fun getMinCost(level: Int): Int {
        return 12 + 20 * (level -1)
    }

    override fun getRarity(): Rarity {
        return Rarity.UNCOMMON
    }

}
