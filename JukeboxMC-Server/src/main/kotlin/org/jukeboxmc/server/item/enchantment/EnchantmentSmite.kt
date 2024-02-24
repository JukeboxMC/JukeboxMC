package org.jukeboxmc.server.item.enchantment

class EnchantmentSmite : JukeboxEnchantment(){

    override fun getId(): Int {
        return 10
    }

    override fun getMaxLevel(): Int {
        return 5
    }

}
