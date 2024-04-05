package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.PressurePlate
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockPressurePlate(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), PressurePlate {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        if (world.getBlock(blockPosition).isTransparent()) {
            return false
        }
        val block = world.getBlock(placePosition)
        if (block is BlockWater && block.getLiquidDepth() == 0) {
            world.setBlock(placePosition, block, 1, false)
        }
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getRedstoneSignal(): Int {
       return this.getIntState("redstone_signal")
   }

   override fun setRedstoneSignal(value: Int): PressurePlate {
       return this.setState("redstone_signal", value)
   }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}