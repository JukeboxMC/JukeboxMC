package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.MobEquipmentPacket
import org.jukeboxmc.api.inventory.Inventory
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.inventory.JukeboxInventory
import org.jukeboxmc.server.inventory.JukeboxPlayerInventory
import org.jukeboxmc.server.inventory.WindowId
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

class MobEquipmentHandler : PacketHandler<MobEquipmentPacket> {

    override fun handle(packet: MobEquipmentPacket, server: JukeboxServer, player: JukeboxPlayer) {
        val windowId = WindowId.getWindowId(packet.containerId)?: return
        val inventory = getInventory(player, windowId)
        if (inventory != null) {
            val item = inventory.getItem(packet.hotbarSlot).toJukeboxItem()
            if (item != JukeboxItem(packet.item, false)) {
                (inventory as JukeboxInventory).sendContents(player)
                return
            }
            if (inventory is JukeboxPlayerInventory) {
                inventory.setItemInHandSlot(packet.hotbarSlot)
                player.setAction(false)
            }
        }
    }

    private fun getInventory(player: JukeboxPlayer, windowId: WindowId): Inventory? {
        return when (windowId) {
            WindowId.PLAYER -> player.getInventory()
            WindowId.CURSOR_DEPRECATED, WindowId.CURSOR -> player.getCursorInventory()
            WindowId.ARMOR_DEPRECATED, WindowId.ARMOR -> player.getArmorInventory()
            else -> player.getCurrentInventory()
        }
    }
}