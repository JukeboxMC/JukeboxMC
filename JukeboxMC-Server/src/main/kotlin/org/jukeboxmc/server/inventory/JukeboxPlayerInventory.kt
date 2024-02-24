package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.cloudburstmc.protocol.bedrock.packet.InventoryContentPacket
import org.cloudburstmc.protocol.bedrock.packet.InventorySlotPacket
import org.cloudburstmc.protocol.bedrock.packet.MobEquipmentPacket
import org.jukeboxmc.api.entity.passive.EntityHuman
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.inventory.PlayerInventory
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.extensions.toJukeboxPlayer
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxPlayerInventory(
    inventoryHolder: InventoryHolder
) : ContainerInventory(inventoryHolder, 36), PlayerInventory {

    private var itemInHandSlot: Int = 0

    override fun getType(): InventoryType {
        return InventoryType.PLAYER
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.INVENTORY
    }

    override fun getInventoryHolder(): JukeboxPlayer {
        return super.getInventoryHolder() as JukeboxPlayer
    }

    override fun sendContents(player: JukeboxPlayer) {
        val inventoryContentPacket = InventoryContentPacket()
        if (player.getCurrentInventory() === this) {
            inventoryContentPacket.containerId = WindowId.OPEN_CONTAINER.getId()
            inventoryContentPacket.contents = getItemDataContents()
            player.sendPacket(inventoryContentPacket)
            return
        }
        inventoryContentPacket.containerId = WindowId.PLAYER.getId()
        inventoryContentPacket.contents = getItemDataContents()
        player.sendPacket(inventoryContentPacket)
    }

    override fun sendContents(slot: Int, player: JukeboxPlayer) {
        if (player.getCurrentInventory() != null && player.getCurrentInventory() === this) {
            val inventorySlotPacket = InventorySlotPacket()
            inventorySlotPacket.slot = slot
            inventorySlotPacket.item = this.getContents()[slot].toJukeboxItem().toItemData()
            inventorySlotPacket.containerId = WindowId.OPEN_CONTAINER.getId()
            player.sendPacket(inventorySlotPacket)
        }

        val inventorySlotPacket = InventorySlotPacket()
        inventorySlotPacket.slot = slot
        inventorySlotPacket.item = this.getContents()[slot].toJukeboxItem().toItemData()
        inventorySlotPacket.containerId = WindowId.PLAYER.getId()
        player.sendPacket(inventorySlotPacket)
    }

    override fun removeViewer(player: JukeboxPlayer) {
        if (player !== this.getInventoryHolder()) {
            super.removeViewer(player)
        }
    }

    override fun setItem(slot: Int, item: Item) {
        val oldItem = getItem(slot)
        super.setItem(slot, item)
        if (slot == itemInHandSlot) {
            oldItem.toJukeboxItem().removeFromHand(this.getInventoryHolder())
            item.toJukeboxItem().addToHand(this.getInventoryHolder())
            this.updateItemInHandForAll()
        }
    }

    override fun getItemInHand(): Item {
        return this.getContents()[this.itemInHandSlot]
    }

    override fun setItemInHand(item: Item) {
        this.setItem(this.itemInHandSlot, item)
        this.sendItemInHand()
    }

    override fun getItemInHandSlot(): Int {
        return this.itemInHandSlot
    }

    override fun setItemInHandSlot(slot: Int) {
        if (itemInHandSlot in 0..8) {
            val oldItem = getItemInHand().toJukeboxItem()
            oldItem.removeFromHand(this.getInventoryHolder())
            itemInHandSlot = slot
            val item = getItemInHand().toJukeboxItem()
            item.addToHand(this.getInventoryHolder())
            updateItemInHandForAll()
        }
    }

    fun sendItemInHand() {
        this.getInventoryHolder().sendPacket(createMobEquipmentPacket(this.getInventoryHolder()))
        this.sendContents(itemInHandSlot, this.getInventoryHolder())
    }

    fun updateItemInHandForAll() {
        val mobEquipmentPacket = this.createMobEquipmentPacket(this.getInventoryHolder())
        for (onlinePlayers in this.getInventoryHolder().getWorld().getPlayers()) {
            if (onlinePlayers != this.getInventoryHolder()) {
                onlinePlayers.toJukeboxPlayer().sendPacket(mobEquipmentPacket)
            }
        }
    }

    fun createMobEquipmentPacket(entityHuman: EntityHuman): MobEquipmentPacket {
        val mobEquipmentPacket = MobEquipmentPacket()
        mobEquipmentPacket.runtimeEntityId = entityHuman.getEntityId()
        mobEquipmentPacket.item = this.getItemInHand().toJukeboxItem().toItemData()
        mobEquipmentPacket.containerId = WindowId.PLAYER.getId()
        mobEquipmentPacket.hotbarSlot = itemInHandSlot
        mobEquipmentPacket.inventorySlot = itemInHandSlot
        return mobEquipmentPacket
    }
}