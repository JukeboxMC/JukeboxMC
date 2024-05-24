package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Enchantment

abstract class JukeboxEnchantmentProtection(private val protectionType: ProtectionType) : JukeboxEnchantment() {

    override fun checkCompatibility(enchantment: Enchantment): Boolean {
        if (enchantment is JukeboxEnchantmentProtection) {
            if (enchantment.protectionType == this.protectionType) {
                return false
            }
            return enchantment.protectionType == ProtectionType.FALL || this.protectionType == ProtectionType.FALL
        }
        return super.checkCompatibility(enchantment)
    }

    enum class ProtectionType {
        PROTECTION,
        FALL,
        FIRE,
        EXPLOSION,
        PROJECTILE
    }

}