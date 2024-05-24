package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Rarity

class EnchantmentFortune : JukeboxEnchantmentLoot() {

    override fun getId(): Int {
        return 18
    }

    override fun getMaxLevel(): Int {
        return 3
    }

    override fun getRarity(): Rarity {
        return Rarity.RARE
    }

}
