package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Lantern
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockLantern(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Lantern {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val hanging =
            blockFace != BlockFace.UP && this.isBlockAboveValid() && (this.isBlockUnderValid() || blockFace == BlockFace.DOWN)
        if (this.isBlockUnderValid() && !hanging) {
            return false
        }
        val block = world.getBlock(placePosition)
        if (block is BlockWater && block.getLiquidDepth() == 0) {
            world.setBlock(placePosition, block, 1, false)
        }
        this.setHanging(hanging)
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    private fun isBlockAboveValid(): Boolean {
        val up = this.getRelative(BlockFace.UP)
        return if (up.getType().isLeaves()) {
            false
        } else if (up.getType().isFence() || up.getType().isWall()) {
            true
        } else if (up is BlockSlab) {
            up.getVerticalHalf() != VerticalHalf.TOP
        } else if (up is BlockStairs) {
            !up.isUpsideDown()
        } else up.isSolid()
    }

    private fun isBlockUnderValid(): Boolean {
        val down = this.getRelative(BlockFace.DOWN)
        return if (down.getType().isLeaves()) {
            true
        } else if (down.getType().isFence() || down.getType().isWall()) {
            false
        } else if (down is BlockSlab) {
            down.getVerticalHalf() != VerticalHalf.TOP
        } else if (down is BlockStairs) {
            !down.isUpsideDown()
        } else !down.isSolid()
    }

    override fun isHanging(): Boolean {
        return this.getBooleanState("hanging")
    }

    override fun setHanging(value: Boolean): Lantern {
        return this.setState("hanging", value.toByte())
    }
}