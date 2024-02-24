package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.WeepingVines
import org.jukeboxmc.server.block.JukeboxBlock

class BlockWeepingVines(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), WeepingVines {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getWeepingVinesAge(): Int {
       return this.getIntState("weeping_vines_age")
   }

   override fun setWeepingVinesAge(value: Int): WeepingVines {
       return this.setState("weeping_vines_age", value)
   }
}