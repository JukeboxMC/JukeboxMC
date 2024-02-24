@file:Suppress("UNCHECKED_CAST")

package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData
import org.jukeboxmc.api.inventory.Inventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import java.util.*

abstract class JukeboxInventory(
    private val inventoryHolder: InventoryHolder,
    private val size: Int,
    private var holderId: Long = -1
) : Inventory {

    private val viewers: MutableSet<JukeboxPlayer> = mutableSetOf()
    private val contents: Array<Item> = arrayOfNulls<Item>(this.size) as Array<Item>

    init {
        if (this.inventoryHolder is Player) {
            this.holderId = this.inventoryHolder.getEntityId()
        }
        Arrays.fill(this.contents, Item.create(ItemType.AIR))
    }

    abstract fun sendContents(player: JukeboxPlayer)

    abstract fun sendContents(slot: Int, player: JukeboxPlayer)

    override fun getInventoryHolder(): InventoryHolder {
        return this.inventoryHolder
    }

    override fun getType(): InventoryType {
        return InventoryType.NONE
    }

    override fun getViewer(): Set<Player> {
        return this.viewers
    }

    fun addViewer(player: JukeboxPlayer) {
        this.viewers.add(player)
    }

    open fun removeViewer(player: JukeboxPlayer) {
        this.viewers.removeIf { it.getUUID() == player.getUUID() }
    }

    fun getHolderId(): Long {
        return this.holderId
    }

    fun setHolderId(holderId: Long) {
        this.holderId = holderId
    }

    override fun getItem(slot: Int): Item {
        return this.contents[slot]
    }

    override fun setItem(slot: Int, item: Item) {
        this.setItem(slot, item, true)
    }

    open fun setItem(slot: Int, item: Item, sendContents: Boolean) {
        if (slot < 0 || slot >= size) {
            return
        }
        if (item.getAmount() <= 0 || item.getType() == ItemType.AIR) {
            this.contents[slot] = Item.create(ItemType.AIR)
        } else {
            this.contents[slot] = item
        }

        if (sendContents) {
            for (player in this.viewers) {
                this.sendContents(slot, player)
            }
        }
    }

    override fun canAddItem(item: Item): Boolean {
        return this.firstPartial(item) != -1
    }

    override fun addItem(vararg items: Item): List<Item> {
        val list: MutableList<Item> = mutableListOf()
        for (i in items.indices) {
            val item: Item = items[i]
            while (true) {
                val firstPartial = firstPartial(item)
                if (firstPartial == -1) {
                    val firstFree = firstEmpty()
                    if (firstFree == -1) {
                        list.add(item)
                        break
                    } else {
                        this.setItem(firstFree, item)
                        break
                    }
                } else {
                    val partialItem: Item = this.getItem(firstPartial)
                    val amount: Int = item.getAmount()
                    val partialAmount: Int = partialItem.getAmount()
                    val maxAmount: Int = partialItem.getMaxStackSize()

                    if (partialItem.getType() == ItemType.AIR) {
                        this.setItem(firstPartial, item)
                        break
                    }

                    if (amount + partialAmount <= maxAmount) {
                        partialItem.setAmount(amount + partialAmount)
                        this.setItem(firstPartial, partialItem)
                        break
                    }
                    partialItem.setAmount(maxAmount)
                    this.setItem(firstPartial, partialItem)
                    item.setAmount(amount + partialAmount - maxAmount)
                }
            }
        }
        return list
    }

    fun addItem(sendContents: Boolean, vararg items: Item): List<Item> {
        val list: MutableList<Item> = mutableListOf()
        for (i in items.indices) {
            val item: Item = items[i]
            while (true) {
                val firstPartial = firstPartial(item)
                if (firstPartial == -1) {
                    val firstFree = firstEmpty()
                    if (firstFree == -1) {
                        list.add(item)
                        break
                    } else {
                        this.setItem(firstFree, item, sendContents)
                        break
                    }
                } else {
                    val partialItem: Item = this.getItem(firstPartial)
                    val amount: Int = item.getAmount()
                    val partialAmount: Int = partialItem.getAmount()
                    val maxAmount: Int = partialItem.getMaxStackSize()

                    if (amount + partialAmount <= maxAmount) {
                        partialItem.setAmount(amount + partialAmount)
                        this.setItem(firstPartial, partialItem, sendContents)
                        break
                    }
                    partialItem.setAmount(maxAmount)
                    this.setItem(firstPartial, partialItem, sendContents)
                    item.setAmount(amount + partialAmount - maxAmount)
                }
            }
        }
        return list
    }

    override fun removeItem(slot: Int) {
        this.setItem(slot, Item.create(ItemType.AIR))
    }

    override fun removeItem(item: Item, amount: Int) {
        for (i in 0 until size) {
            val content = getItem(i)
            if (content.getType() !== ItemType.AIR) {
                if (content.getType() == item.getType() && content.getMeta() == item.getMeta()) {
                    content.setAmount(content.getAmount() - amount)
                    if (content.getAmount() <= 0) {
                        this.setItem(i, Item.create(ItemType.AIR))
                    } else {
                        this.setItem(i, content)
                    }
                    break
                }
            }
        }
    }

    override fun firstEmpty(): Int {
        val inventory: Array<Item> = getContents()
        for (i in inventory.indices) {
            if (inventory[i].getType() == ItemType.AIR) {
                return i
            }
        }
        return -1
    }

    override fun firstPartial(item: Item): Int {
        val inventory: Array<Item> = this.getContents()
        if (item.getType() == ItemType.AIR) {
            return -1
        }
        for (i in inventory.indices) {
            val cItem: Item = inventory[i]
            if (cItem.getType() == ItemType.AIR) return i
            if (cItem.getType() != ItemType.AIR && cItem.getAmount() < cItem.getMaxStackSize() && cItem.isSimilar(item)) {
                return i
            }
        }
        return -1
    }

    override fun clear() {
        for (i in 0 until size) {
            val item = getItem(i)
            if (item.getType() !== ItemType.AIR) {
                this.removeItem(i)
            }
        }
    }

    override fun getSize(): Int {
        return this.size
    }

    override fun getContents(): Array<Item> {
        return this.contents
    }

    override fun contains(itemType: ItemType, amount: Int): Boolean {
        var remainingAmount = amount
        if (remainingAmount <= 0) {
            return true
        }
        for (content in this.contents) {
            if (content.getType() == itemType) {
                remainingAmount -= content.getAmount()
                if (remainingAmount <= 0) {
                    return true
                }
            }
        }
        return false
    }

    override fun contains(itemType: ItemType): Boolean {
        return this.contains(itemType, 1)
    }

    open fun getItemDataContents(): List<ItemData>? {
        val itemDataList: MutableList<ItemData> = ArrayList()
        for (content in getContents()) {
            itemDataList.add(content.toJukeboxItem().toItemData())
        }
        return itemDataList
    }
}