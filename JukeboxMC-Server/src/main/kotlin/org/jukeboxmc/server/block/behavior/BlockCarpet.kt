package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.UpdateReason
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockCarpet(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val block = world.getBlock(placePosition)
        if (block is BlockWater && block.getLiquidDepth() == 0) {
            world.setBlock(placePosition, block, 1, false)
        }
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun onUpdate(updateReason: UpdateReason): Long {
        val blockDown = this.getRelative(BlockFace.DOWN)
        if (updateReason == UpdateReason.NORMAL) {
            if (blockDown.getType() == BlockType.AIR || blockDown.getType() == BlockType.FIRE) { //TODO Add liquid check
                this.getWorld().setBlock(this.getLocation(), AIR)
                this.getWorld().dropItemNaturally(this.getLocation(), this.toItem())
            }
        }
        return -1
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }

}