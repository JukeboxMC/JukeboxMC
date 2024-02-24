package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Cauldron
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.LiquidType
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockCauldron(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Cauldron {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        BlockEntity.create(BlockEntityType.CAULDRON, this)?.spawn()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

   override fun getFillLevel(): Int {
       return this.getIntState("fill_level")
   }

   override fun setFillLevel(value: Int): BlockCauldron {
       return this.setState("fill_level", value)
   }

   override fun getCauldronLiquid(): LiquidType {
       return LiquidType.valueOf(this.getStringState("cauldron_liquid"))
   }

   override fun setCauldronLiquid(value: LiquidType): BlockCauldron {
       return this.setState("cauldron_liquid", value.name.lowercase())
   }
}