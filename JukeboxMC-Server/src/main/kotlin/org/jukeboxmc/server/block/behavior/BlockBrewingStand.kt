package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BrewingStand
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.extensions.toJukeboxBlockEntity
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockBrewingStand(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    BrewingStand {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        BlockEntity.create(BlockEntityType.BREWINGSTAND, this)?.spawn()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun interact(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        world.getBlockEntity(this.getLocation())?.toJukeboxBlockEntity()
            ?.interact(player, blockPosition, clickedPosition, blockFace, itemInHand)
        return true
    }

   override fun isBrewingStandSlotC(): Boolean {
       return this.getBooleanState("brewing_stand_slot_c_bit")
   }

   override fun setBrewingStandSlotC(value: Boolean): BlockBrewingStand {
       return this.setState("brewing_stand_slot_c_bit", value.toByte())
   }

   override fun isBrewingStandSlotA(): Boolean {
       return this.getBooleanState("brewing_stand_slot_a_bit")
   }

   override fun setBrewingStandSlotA(value: Boolean): BlockBrewingStand {
       return this.setState("brewing_stand_slot_a_bit", value.toByte())
   }

   override fun isBrewingStandSlotB(): Boolean {
       return this.getBooleanState("brewing_stand_slot_b_bit")
   }

   override fun setBrewingStandSlotB(value: Boolean): BlockBrewingStand {
       return this.setState("brewing_stand_slot_b_bit", value.toByte())
   }
}