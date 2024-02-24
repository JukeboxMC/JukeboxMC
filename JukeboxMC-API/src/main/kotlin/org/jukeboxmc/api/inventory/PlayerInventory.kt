package org.jukeboxmc.api.inventory

import org.jukeboxmc.api.item.Item

interface PlayerInventory : Inventory {

    fun getItemInHand(): Item

    fun setItemInHand(item: Item)

    fun getItemInHandSlot(): Int

    fun setItemInHandSlot(slot: Int)

}