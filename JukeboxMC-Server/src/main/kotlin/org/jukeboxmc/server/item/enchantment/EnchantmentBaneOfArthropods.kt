package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentBaneOfArthropods : JukeboxEnchantmentDamage() {

    override fun getId(): Int {
        return 11
    }

    override fun getMaxLevel(): Int {
        return 5
    }

    override fun getMinCost(level: Int): Int {
        return 5 + (level - 1) * 8
    }

    override fun getMaxCost(level: Int): Int {
        return this.getMinCost(level) + 20
    }

    override fun getRarity(): Rarity {
        return Rarity.UNCOMMON
    }

}
