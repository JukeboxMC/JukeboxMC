package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentFireProtection : JukeboxEnchantmentProtection(ProtectionType.FIRE) {

    override fun getId(): Int {
        return 1
    }

    override fun getMaxLevel(): Int {
        return 4
    }

    override fun getMinCost(level: Int): Int {
        return 10 + (level - 1) * 8
    }

    override fun getMaxCost(level: Int): Int {
        return this.getMinCost(level) + 8
    }

    override fun getRarity(): Rarity {
        return Rarity.UNCOMMON
    }

}
