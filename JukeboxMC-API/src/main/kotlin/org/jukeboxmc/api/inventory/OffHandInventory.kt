package org.jukeboxmc.api.inventory

import org.jukeboxmc.api.item.Item

interface OffHandInventory : Inventory {

    fun getOffHandItem(): Item

    fun setOffHandItem(item: Item)
}