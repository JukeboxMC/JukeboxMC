package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SweetBerryBush
import org.jukeboxmc.server.block.JukeboxBlock

class BlockSweetBerryBush(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), SweetBerryBush {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getGrowth(): Int {
       return this.getIntState("growth")
   }

   override fun setGrowth(value: Int): SweetBerryBush {
       return this.setState("growth", value)
   }
}