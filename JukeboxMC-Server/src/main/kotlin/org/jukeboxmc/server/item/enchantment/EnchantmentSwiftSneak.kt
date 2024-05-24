package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentSwiftSneak : JukeboxEnchantment(){

    override fun getId(): Int {
        return 37
    }

    override fun getMaxLevel(): Int {
        return 3
    }

    override fun getMinCost(level: Int): Int {
        return 10 * level
    }

    override fun getRarity(): Rarity {
        return Rarity.VERY_RARE
    }

}
