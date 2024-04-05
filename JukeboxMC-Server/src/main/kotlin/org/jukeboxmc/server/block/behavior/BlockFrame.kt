package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Frame
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockFrame(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Frame {

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
        this.setFacingDirection(player.getDirection().toBlockFace().opposite())
        BlockEntity.create(BlockEntityType.ITEMFRAME, this)?.spawn()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getFacingDirection(): BlockFace {
        return BlockFace.entries[this.getIntState("facing_direction")]
    }

    override fun setFacingDirection(value: BlockFace): Frame {
        return this.setState("facing_direction", value.ordinal)
    }

    override fun isItemFrameMap(): Boolean {
        return this.getBooleanState("item_frame_map_bit")
    }

    override fun setItemFrameMap(value: Boolean): Frame {
        return this.setState("item_frame_map_bit", value.toByte())
    }

    override fun isItemFramePhoto(): Boolean {
        return this.getBooleanState("item_frame_photo_bit")
    }

    override fun setItemFramePhoto(value: Boolean): Frame {
        return this.setState("item_frame_photo_bit", value.toByte())
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}