package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Leaves
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockLeaves(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Leaves {

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

    override fun isUpdate(): Boolean {
        return this.getBooleanState("update_bit")
    }

    override fun setUpdate(value: Boolean): Leaves {
        return this.setState("update_bit", value.toByte())
    }

    override fun isPersistent(): Boolean {
        return this.getBooleanState("persistent_bit")
    }

    override fun setPersistent(value: Boolean): Leaves {
        return this.setState("persistent_bit", value.toByte())
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}