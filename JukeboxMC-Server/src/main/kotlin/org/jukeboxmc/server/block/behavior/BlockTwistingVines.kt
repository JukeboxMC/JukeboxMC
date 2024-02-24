package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.TwistingVines
import org.jukeboxmc.server.block.JukeboxBlock

class BlockTwistingVines(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), TwistingVines {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getTwistingVinesAge(): Int {
       return this.getIntState("twisting_vines_age")
   }

   override fun setTwistingVinesAge(value: Int): TwistingVines {
       return this.setState("twisting_vines_age", value)
   }
}