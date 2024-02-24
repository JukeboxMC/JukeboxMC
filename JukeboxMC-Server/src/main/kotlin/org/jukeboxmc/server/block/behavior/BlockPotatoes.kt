package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Potatoes
import org.jukeboxmc.server.block.JukeboxBlock

class BlockPotatoes(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Potatoes {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getGrowth(): Int {
       return this.getIntState("growth")
   }

   override fun setGrowth(value: Int): Potatoes {
       return this.setState("growth", value)
   }
}