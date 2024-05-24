package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentProtection : JukeboxEnchantmentProtection(ProtectionType.PROTECTION) {

    override fun getId(): Int {
        return 0
    }

    override fun getMaxLevel(): Int {
        return 4
    }

    override fun getMinCost(level: Int): Int {
        return 1 + (level - 1) * 11
    }

    override fun getMaxCost(level: Int): Int {
        return this.getMinCost(level) + 11
    }

    override fun getRarity(): Rarity {
        return Rarity.COMMON
    }

}
