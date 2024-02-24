package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Composter
import org.jukeboxmc.server.block.JukeboxBlock

class BlockComposter(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Composter {

   override fun getComposterFillLevel(): Int {
       return this.getIntState("composter_fill_level")
   }

   override fun setComposterFillLevel(value: Int): Composter {
       return this.setState("composter_fill_level", value)
   }
}