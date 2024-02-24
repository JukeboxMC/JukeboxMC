package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Tnt
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockTnt(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Tnt {

   override fun isExplode(): Boolean {
       return this.getBooleanState("explode_bit")
   }

   override fun setExplode(value: Boolean): Tnt {
       return this.setState("explode_bit", value.toByte())
   }

   override fun isAllowUnderwater(): Boolean {
       return this.getBooleanState("allow_underwater_bit")
   }

   override fun setAllowUnderwater(value: Boolean): Tnt {
       return this.setState("allow_underwater_bit", value.toByte())
   }
}