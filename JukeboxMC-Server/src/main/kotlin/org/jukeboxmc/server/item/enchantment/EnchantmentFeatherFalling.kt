package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentFeatherFalling : JukeboxEnchantmentProtection(ProtectionType.FALL) {

    override fun getId(): Int {
        return 2
    }

    override fun getMaxLevel(): Int {
        return 4
    }

    override fun getMinCost(level: Int): Int {
        return 5 + (level - 1) * 6
    }

    override fun getMaxCost(level: Int): Int {
        return this.getMinCost(level) + 6
    }

    override fun getRarity(): Rarity {
        return Rarity.COMMON
    }
}
