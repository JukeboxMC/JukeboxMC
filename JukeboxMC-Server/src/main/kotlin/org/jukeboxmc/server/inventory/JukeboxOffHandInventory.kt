package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.packet.InventoryContentPacket
import org.cloudburstmc.protocol.bedrock.packet.InventorySlotPacket
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.inventory.OffHandInventory
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.server.entity.passive.JukeboxEntityHuman
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxOffHandInventory(
    inventoryHolder: InventoryHolder
) : JukeboxInventory(inventoryHolder, 1), OffHandInventory {

    override fun sendContents(player: JukeboxPlayer) {
        val inventoryContentPacket = InventoryContentPacket()
        inventoryContentPacket.containerId = WindowId.OFFHAND_DEPRECATED.getId()
        inventoryContentPacket.contents = getItemDataContents()
        player.sendPacket(inventoryContentPacket)
    }

    override fun sendContents(slot: Int, player: JukeboxPlayer) {
        val inventorySlotPacket = InventorySlotPacket()
        inventorySlotPacket.containerId = WindowId.OFFHAND_DEPRECATED.getId()
        inventorySlotPacket.item = this.getContents()[slot].toJukeboxItem().toItemData()
        inventorySlotPacket.slot = slot
        player.sendPacket(inventorySlotPacket)
    }

    override fun getInventoryHolder(): JukeboxEntityHuman {
        return super.getInventoryHolder() as JukeboxEntityHuman
    }

    override fun getType(): InventoryType {
        return InventoryType.OFFHAND
    }

    override fun getOffHandItem(): Item {
        return this.getContents()[0]
    }

    override fun setOffHandItem(item: Item) {
        this.setItem(0, item)
    }
}