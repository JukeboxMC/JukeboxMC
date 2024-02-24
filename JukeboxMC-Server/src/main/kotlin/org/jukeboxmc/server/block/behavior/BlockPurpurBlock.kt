package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.PurpurBlock
import org.jukeboxmc.api.block.data.Axis
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.ChiselType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockPurpurBlock(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), PurpurBlock {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        this.setPillarAxis(blockFace.toAxis())
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

   override fun getPillarAxis(): Axis {
       return Axis.valueOf(this.getStringState("pillar_axis"))
   }

   override fun setPillarAxis(value: Axis): PurpurBlock {
       return this.setState("pillar_axis", value.name.lowercase())
   }

   override fun getChiselType(): ChiselType {
       return ChiselType.valueOf(this.getStringState("chisel_type"))
   }

   override fun setChiselType(value: ChiselType): PurpurBlock {
       return this.setState("chisel_type", value.name.lowercase())
   }
}