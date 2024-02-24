package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Crafter
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Orientation
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockCrafter(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Crafter {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        BlockEntity.create(BlockEntityType.COUNT, this)?.spawn()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

   override fun isTriggered(): Boolean {
       return this.getBooleanState("triggered_bit")
   }

   override fun setTriggered(value: Boolean): BlockCrafter {
       return this.setState("triggered_bit", value.toByte())
   }

   override fun getOrientation(): Orientation {
       return Orientation.valueOf(this.getStringState("orientation"))
   }

   override fun setOrientation(value: Orientation): BlockCrafter {
       return this.setState("orientation", value.name.lowercase())
   }

   override fun isCrafting(): Boolean {
       return this.getBooleanState("crafting")
   }

   override fun setCrafting(value: Boolean): BlockCrafter {
       return this.setState("crafting", value.toByte())
   }
}