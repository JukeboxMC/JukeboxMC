package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.cloudburstmc.protocol.bedrock.packet.BlockEventPacket
import org.jukeboxmc.api.inventory.ChestInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.server.blockentity.JukeboxBlockEntityChest
import org.jukeboxmc.server.extensions.toVector3i
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxChestInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 27),
    ChestInventory {

    override fun getInventoryHolder(): JukeboxBlockEntityChest {
        return super.getInventoryHolder() as JukeboxBlockEntityChest
    }

    override fun getType(): InventoryType {
        return InventoryType.CHEST
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.CONTAINER
    }

    override fun onOpen(player: JukeboxPlayer) {
        super.onOpen(player)
        if (this.getViewer().size == 1) {
            val location = getInventoryHolder().getBlock().getLocation()
            val blockEventPacket = BlockEventPacket()
            blockEventPacket.blockPosition = location.toVector3i()
            blockEventPacket.eventType = 1
            blockEventPacket.eventData = 2
            player.getWorld().playLevelSound(location, SoundEvent.CHEST_OPEN)
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
            player.getWorld().playLevelSound(location, SoundEvent.CHEST_CLOSED)
            player.getWorld().sendChunkPacket(location.getChunkX(), location.getChunkZ(), blockEventPacket)
        }
    }

}