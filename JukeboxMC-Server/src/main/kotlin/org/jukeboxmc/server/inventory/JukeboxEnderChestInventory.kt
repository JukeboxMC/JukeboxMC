package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.cloudburstmc.protocol.bedrock.packet.BlockEventPacket
import org.jukeboxmc.api.inventory.EnderChestInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.server.extensions.toVector3i
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxEnderChestInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 27), EnderChestInventory {

    init {
        this.setHolderId(-1)
    }

    private var location: Vector? = null

    override fun getInventoryHolder(): Player {
        return super.getInventoryHolder() as Player
    }

    override fun getType(): InventoryType {
        return InventoryType.ENDERCHEST
    }

    override fun onOpen(player: JukeboxPlayer) {
        super.onOpen(player)
        if (this.getViewer().size == 1) {
            this.location?.let {
                val blockEventPacket = BlockEventPacket()
                blockEventPacket.blockPosition = it.toVector3i()
                blockEventPacket.eventType = 1
                blockEventPacket.eventData = 2
                player.getWorld().playLevelSound(it, SoundEvent.ENDERCHEST_OPEN)
                player.getWorld().sendChunkPacket(it.getChunkX(), it.getChunkZ(), blockEventPacket)
            }
        }
    }

    override fun onClose(player: JukeboxPlayer) {
        if (this.getViewer().isEmpty()) {
            this.location?.let {
                val blockEventPacket = BlockEventPacket()
                blockEventPacket.blockPosition = it.toVector3i()
                blockEventPacket.eventType = 1
                blockEventPacket.eventData = 0
                player.getWorld().playLevelSound(it, SoundEvent.ENDERCHEST_CLOSED)
                player.getWorld().sendChunkPacket(it.getChunkX(), it.getChunkZ(), blockEventPacket)
            }
        }
    }

    fun getLocation(): Vector? {
        return this.location
    }

    fun setLocation(location: Vector) {
        this.location = location
    }
}