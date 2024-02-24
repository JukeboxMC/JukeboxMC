package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.cloudburstmc.protocol.bedrock.packet.BlockEventPacket
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.inventory.ShulkerBoxInventory
import org.jukeboxmc.server.blockentity.JukeboxBlockEntityShulkerBox
import org.jukeboxmc.server.extensions.toVector3i
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxShulkerBoxInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 27), ShulkerBoxInventory {

    override fun getInventoryHolder(): JukeboxBlockEntityShulkerBox {
        return super.getInventoryHolder() as JukeboxBlockEntityShulkerBox
    }

    override fun getType(): InventoryType {
        return InventoryType.SHULKERBOX
    }

    override fun onOpen(player: JukeboxPlayer) {
        super.onOpen(player)
        if (this.getViewer().size == 1) {
            val location = this.getInventoryHolder().getBlock().getLocation()
            val blockEventPacket = BlockEventPacket()
            blockEventPacket.blockPosition = location.toVector3i()
            blockEventPacket.eventType = 1
            blockEventPacket.eventData = 2
            player.getWorld().playLevelSound(location, SoundEvent.SHULKERBOX_OPEN)
            player.getWorld().sendChunkPacket(location.getChunkX(), location.getChunkZ(), blockEventPacket)
        }
    }

    override fun onClose(player: JukeboxPlayer) {
        if (this.getViewer().isEmpty()) {
            val location = getInventoryHolder().getBlock().getLocation()
            val blockEventPacket = BlockEventPacket()
            blockEventPacket.blockPosition = location.toVector3i()
            blockEventPacket.eventType = 1
            blockEventPacket.eventData = 0
            player.getWorld().playLevelSound(location, SoundEvent.SHULKERBOX_CLOSED)
            player.getWorld().sendChunkPacket(location.getChunkX(), location.getChunkZ(), blockEventPacket)
        }
    }

}