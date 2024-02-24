package org.jukeboxmc.api.inventory

import org.jukeboxmc.api.item.Item

interface ArmorInventory : Inventory {

    fun getHelmet(): Item

    fun setHelmet(item: Item)

    fun getChestplate(): Item

    fun setChestplate(item: Item)

    fun getLeggings(): Item

    fun setLeggings(item: Item)

    fun getBoots(): Item

    fun setBoots(item: Item)
}