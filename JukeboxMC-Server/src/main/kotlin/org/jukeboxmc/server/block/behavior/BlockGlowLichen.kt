package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.GlowLichen
import org.jukeboxmc.server.block.JukeboxBlock

class BlockGlowLichen(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), GlowLichen {

   override fun getMultiFaceDirections(): Int {
       return this.getIntState("multi_face_direction_bits")
   }

   override fun setMultiFaceDirections(value: Int): GlowLichen {
       return this.setState("multi_face_direction_bits", value)
   }
}