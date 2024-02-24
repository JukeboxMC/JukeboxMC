package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Carrots
import org.jukeboxmc.server.block.JukeboxBlock

class BlockCarrots(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Carrots {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getGrowth(): Int {
       return this.getIntState("growth")
   }

   override fun setGrowth(value: Int): BlockCarrots {
       return this.setState("growth", value)
   }
}