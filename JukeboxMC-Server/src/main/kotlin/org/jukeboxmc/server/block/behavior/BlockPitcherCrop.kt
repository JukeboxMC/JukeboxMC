package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.PitcherCrop
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockPitcherCrop(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), PitcherCrop {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun isUpperBlock(): Boolean {
       return this.getBooleanState("upper_block_bit")
   }

   override fun setUpperBlock(value: Boolean): PitcherCrop {
       return this.setState("upper_block_bit", value.toByte())
   }

   override fun getGrowth(): Int {
       return this.getIntState("growth")
   }

   override fun setGrowth(value: Int): PitcherCrop {
       return this.setState("growth", value)
   }
}