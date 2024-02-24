package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.jukeboxmc.api.inventory.BarrelInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.server.block.behavior.BlockBarrel
import org.jukeboxmc.server.blockentity.JukeboxBlockEntityBarrel
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxBarrelInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 27),
    BarrelInventory {

    override fun getInventoryHolder(): JukeboxBlockEntityBarrel {
        return super.getInventoryHolder() as JukeboxBlockEntityBarrel
    }

    override fun getType(): InventoryType {
        return InventoryType.BARREL
    }

    override fun onOpen(player: JukeboxPlayer) {
        super.onOpen(player)
        if (this.getViewer().size == 1) {
            val block = this.getInventoryHolder().getBlock()
            if (block is BlockBarrel) {
                if (!block.isOpen()) {
                    block.setOpen(true)
                    player.getWorld().playLevelSound(block.getLocation(), SoundEvent.BARREL_OPEN)
                }
            }
        }
    }

    override fun onClose(player: JukeboxPlayer) {
        super.onClose(player)
        if (this.getViewer().isEmpty()) {
            val block = this.getInventoryHolder().getBlock()
            if (block is BlockBarrel) {
                if (block.isOpen()) {
                    block.setOpen(false)
                    player.getWorld().playLevelSound(block.getLocation(), SoundEvent.BARREL_CLOSE)
                }
            }
        }
    }

}