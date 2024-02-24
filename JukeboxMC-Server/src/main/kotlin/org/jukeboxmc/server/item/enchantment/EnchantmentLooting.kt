package org.jukeboxmc.server.item.enchantment

class EnchantmentLooting : JukeboxEnchantment() {

    override fun getId(): Int {
        return 14
    }

    override fun getMaxLevel(): Int {
        return 3
    }

}
