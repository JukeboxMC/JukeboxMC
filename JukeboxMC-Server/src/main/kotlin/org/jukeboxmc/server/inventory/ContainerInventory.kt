package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.cloudburstmc.protocol.bedrock.packet.ContainerOpenPacket
import org.cloudburstmc.protocol.bedrock.packet.InventoryContentPacket
import org.cloudburstmc.protocol.bedrock.packet.InventorySlotPacket
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.extensions.toVector3i
import org.jukeboxmc.server.player.JukeboxPlayer

abstract class ContainerInventory(
    inventoryHolder: InventoryHolder,
    size: Int
) : JukeboxInventory(inventoryHolder, size) {

    open fun getContainerType(): ContainerType {
        return ContainerType.CONTAINER
    }

    override fun sendContents(player: JukeboxPlayer) {
        val inventoryContentPacket = InventoryContentPacket()
        inventoryContentPacket.containerId = WindowId.OPEN_CONTAINER.getId()
        inventoryContentPacket.contents = this.getItemDataContents()
        player.sendPacket(inventoryContentPacket)
    }

    override fun sendContents(slot: Int, player: JukeboxPlayer) {
        val inventorySlotPacket = InventorySlotPacket()
        inventorySlotPacket.containerId = WindowId.OPEN_CONTAINER.getId()
        inventorySlotPacket.slot = slot
        inventorySlotPacket.item = this.getContents()[slot].toJukeboxItem().toItemData()
        player.sendPacket(inventorySlotPacket)
    }

    open fun addViewer(player: JukeboxPlayer, location: Vector, windowId: Byte) {
        val containerOpenPacket = ContainerOpenPacket()
        containerOpenPacket.uniqueEntityId = this.getHolderId()
        containerOpenPacket.id = windowId
        containerOpenPacket.type = this.getContainerType()
        containerOpenPacket.blockPosition = location.toVector3i()
        player.sendPacket(containerOpenPacket)
        super.addViewer(player)
        this.onOpen(player)
    }

    override fun removeViewer(player: JukeboxPlayer) {
        super.removeViewer(player)
        this.onClose(player)
    }

    open fun onOpen(player: JukeboxPlayer) {
        this.sendContents(player)
    }

    open fun onClose(player: JukeboxPlayer) {}
}