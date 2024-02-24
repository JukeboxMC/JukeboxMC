package org.jukeboxmc.api.inventory

import org.jukeboxmc.api.item.Item

interface CursorInventory : Inventory{

    fun getCursorItem(): Item

    fun setCursorItem(item: Item)
}