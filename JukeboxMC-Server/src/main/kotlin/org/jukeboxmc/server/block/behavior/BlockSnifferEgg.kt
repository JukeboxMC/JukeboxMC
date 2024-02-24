package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SnifferEgg
import org.jukeboxmc.api.block.data.CrackedState
import org.jukeboxmc.server.block.JukeboxBlock

class BlockSnifferEgg(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), SnifferEgg {

   override fun getCrackedState(): CrackedState {
       return CrackedState.valueOf(this.getStringState("cracked_state"))
   }

   override fun setCrackedState(value: CrackedState): SnifferEgg {
       return this.setState("cracked_state", value.name.lowercase())
   }
}