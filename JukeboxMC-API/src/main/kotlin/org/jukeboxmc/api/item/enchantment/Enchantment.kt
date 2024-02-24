package org.jukeboxmc.api.item.enchantment

interface Enchantment {

    fun getId(): Int

    fun getLevel(): Int

    fun getMaxLevel(): Int

    override fun equals(other: Any?): Boolean

}