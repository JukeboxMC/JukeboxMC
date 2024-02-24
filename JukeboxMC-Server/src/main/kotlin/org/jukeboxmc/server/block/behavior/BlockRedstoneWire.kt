package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.RedstoneWire
import org.jukeboxmc.server.block.JukeboxBlock

class BlockRedstoneWire(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), RedstoneWire {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getRedstoneSignal(): Int {
       return this.getIntState("redstone_signal")
   }

   override fun setRedstoneSignal(value: Int): RedstoneWire {
       return this.setState("redstone_signal", value)
   }
}