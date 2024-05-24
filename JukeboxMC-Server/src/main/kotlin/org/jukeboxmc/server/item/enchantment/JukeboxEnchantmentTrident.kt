package org.jukeboxmc.server.item.enchantment

abstract class JukeboxEnchantmentTrident : JukeboxEnchantment() {

    override fun getMaxCost(level: Int): Int {
        return 50
    }
}