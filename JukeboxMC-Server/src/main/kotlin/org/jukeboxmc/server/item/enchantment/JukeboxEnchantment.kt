package org.jukeboxmc.server.item.enchantment

import org.jukeboxmc.api.item.enchantment.Enchantment
import org.jukeboxmc.api.item.enchantment.EnchantmentType

abstract class JukeboxEnchantment : Enchantment {

    companion object {
        fun create(enchantmentType: EnchantmentType): Enchantment {
            return EnchantmentRegistry.getEnchantmentClass(enchantmentType).getConstructor().newInstance()
        }
    }

    private var level: Int = 1

    override fun getLevel(): Int {
        return this.level
    }

    fun setLevel(level: Int) {
        this.level = level
    }

    override fun getMinCost(level: Int): Int {
        return 1 + level * 10
    }

    override fun getMaxCost(level: Int): Int {
        return this.getMinCost(level) + 5
    }

    override fun checkCompatibility(enchantment: Enchantment): Boolean {
        return this != enchantment
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