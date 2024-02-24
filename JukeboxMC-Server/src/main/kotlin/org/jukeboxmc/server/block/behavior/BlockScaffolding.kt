package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Scaffolding
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockScaffolding(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Scaffolding {

   override fun getStability(): Int {
       return this.getIntState("stability")
   }

   override fun setStability(value: Int): Scaffolding {
       return this.setState("stability", value)
   }

   override fun isStabilityCheck(): Boolean {
       return this.getBooleanState("stability_check")
   }

   override fun setStabilityCheck(value: Boolean): Scaffolding {
       return this.setState("stability_check", value.toByte())
   }
}