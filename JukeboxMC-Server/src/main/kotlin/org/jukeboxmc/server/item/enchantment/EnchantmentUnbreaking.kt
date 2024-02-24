package org.jukeboxmc.server.item.enchantment

class EnchantmentUnbreaking: JukeboxEnchantment() {

    override fun getId(): Int {
        return 17
    }

    override fun getMaxLevel(): Int {
        return 3
    }

}
