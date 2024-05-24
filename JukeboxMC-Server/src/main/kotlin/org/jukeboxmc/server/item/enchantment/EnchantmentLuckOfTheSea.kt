package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentLuckOfTheSea : JukeboxEnchantmentLoot() {

    override fun getId(): Int {
        return 23
    }

    override fun getMaxLevel(): Int {
        return 3
    }

    override fun getRarity(): Rarity {
        return Rarity.RARE
    }
}
