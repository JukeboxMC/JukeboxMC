package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.PressurePlate
import org.jukeboxmc.server.block.JukeboxBlock

class BlockPressurePlate(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), PressurePlate {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getRedstoneSignal(): Int {
       return this.getIntState("redstone_signal")
   }

   override fun setRedstoneSignal(value: Int): PressurePlate {
       return this.setState("redstone_signal", value)
   }
}