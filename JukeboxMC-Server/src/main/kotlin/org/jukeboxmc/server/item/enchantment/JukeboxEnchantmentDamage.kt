package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Enchantment

abstract class JukeboxEnchantmentDamage : JukeboxEnchantment(){

    override fun checkCompatibility(enchantment: Enchantment): Boolean {
        return enchantment !is JukeboxEnchantmentDamage
    }

}