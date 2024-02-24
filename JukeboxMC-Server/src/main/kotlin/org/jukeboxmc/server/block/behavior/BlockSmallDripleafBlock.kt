package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SmallDripleafBlock
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockSmallDripleafBlock(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), SmallDripleafBlock {

   override fun isUpperBlock(): Boolean {
       return this.getBooleanState("upper_block_bit")
   }

   override fun setUpperBlock(value: Boolean): SmallDripleafBlock {
       return this.setState("upper_block_bit", value.toByte())
   }

   override fun getCardinalDirection(): Direction {
       return Direction.valueOf(this.getStringState("minecraft:cardinal_direction"))
   }

   override fun setCardinalDirection(value: Direction): SmallDripleafBlock {
       return this.setState("minecraft:cardinal_direction", value.name.lowercase())
   }
}