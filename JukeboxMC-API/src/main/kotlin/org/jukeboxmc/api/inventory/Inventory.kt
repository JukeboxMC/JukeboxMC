package org.jukeboxmc.api.inventory

import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.player.Player

interface Inventory {

    fun getType(): InventoryType

    fun getInventoryHolder(): InventoryHolder

    fun getViewer(): Set<Player>

    fun getItem(slot: Int): Item

    fun setItem(slot: Int, item: Item)

    fun canAddItem(item: Item): Boolean

    fun addItem(vararg items: Item): List<Item>

    fun removeItem(slot: Int)

    fun removeItem(item: Item, amount: Int)

    fun removeItem(slot: Int, item: Item, amount: Int)

    fun firstEmpty(): Int

    fun firstPartial(item: Item): Int

    fun clear()

    fun getSize(): Int

    fun getContents(): Array<Item>

    fun contains(itemType: ItemType, amount: Int): Boolean

    fun contains(itemType: ItemType): Boolean

}