package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Enchantment
import org.jukeboxmc.api.item.enchantment.EnchantmentType

abstract class JukeboxEnchantment : Enchantment {

    private var level: Int = 1

    override fun getLevel(): Int {
        return this.level
    }

    fun setLevel(level: Int) {
        this.level = level
    }

    companion object {
        fun create(enchantmentType: EnchantmentType): Enchantment {
            return EnchantmentRegistry.getEnchantmentClass(enchantmentType).getConstructor().newInstance()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Enchantment) return false
        return other.getId() == this.getId() && other.getLevel() == this.getLevel()
    }

    override fun hashCode(): Int {
        return this.getId()
    }

    override fun toString(): String {
        return "JukeboxEnchantment(id=${getId()}, level=$level, maxLevel=${getMaxLevel()})"
    }
}