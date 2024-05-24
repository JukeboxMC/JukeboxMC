package org.jukeboxmc.api.item.enchantment

interface Enchantment {

    fun getId(): Int

    fun getLevel(): Int

    fun getMaxLevel(): Int

    fun getMinCost(level: Int): Int

    fun getMaxCost(level: Int): Int

    fun checkCompatibility(enchantment: Enchantment): Boolean

    fun getRarity(): Rarity

    override fun equals(other: Any?): Boolean

}