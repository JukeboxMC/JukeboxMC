package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.GlowFrame
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockGlowFrame(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), GlowFrame {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        this.setFacingDirection(player.getDirection().toBlockFace().opposite())
        BlockEntity.create(BlockEntityType.GLOWITEMFRAME, this)?.spawn()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getFacingDirection(): BlockFace {
       return BlockFace.entries[this.getIntState("facing_direction")]
   }

   override fun setFacingDirection(value: BlockFace): GlowFrame {
       return this.setState("facing_direction", value.ordinal)
   }

   override fun isItemFrameMap(): Boolean {
       return this.getBooleanState("item_frame_map_bit")
   }

   override fun setItemFrameMap(value: Boolean): GlowFrame {
       return this.setState("item_frame_map_bit", value.toByte())
   }

   override fun isItemFramePhoto(): Boolean {
       return this.getBooleanState("item_frame_photo_bit")
   }

   override fun setItemFramePhoto(value: Boolean): GlowFrame {
       return this.setState("item_frame_photo_bit", value.toByte())
   }
}