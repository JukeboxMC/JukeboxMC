package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.packet.InventorySlotPacket
import org.jukeboxmc.api.inventory.CursorInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxCursorInventory(
    inventoryHolder: InventoryHolder,
) : JukeboxInventory(inventoryHolder, 1), CursorInventory {

    override fun getInventoryHolder(): JukeboxPlayer {
        return super.getInventoryHolder() as JukeboxPlayer
    }

    override fun getType(): InventoryType {
        return InventoryType.CURSOR
    }

    override fun sendContents(player: JukeboxPlayer) {
        this.sendContents(0, player)
    }

    override fun sendContents(slot: Int, player: JukeboxPlayer) {
        val inventorySlotPacket = InventorySlotPacket()
        inventorySlotPacket.containerId = WindowId.CURSOR_DEPRECATED.getId()
        inventorySlotPacket.item = this.getContents()[slot].toJukeboxItem().toItemData()
        inventorySlotPacket.slot = slot
        player.sendPacket(inventorySlotPacket)
    }

    override fun getCursorItem(): Item {
        return this.getContents()[0]
    }

    override fun setCursorItem(item: Item) {
        this.setItem(0, item)
    }

}