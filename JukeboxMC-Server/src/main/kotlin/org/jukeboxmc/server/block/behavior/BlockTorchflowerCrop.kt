package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.TorchflowerCrop
import org.jukeboxmc.server.block.JukeboxBlock

class BlockTorchflowerCrop(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), TorchflowerCrop {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getGrowth(): Int {
       return this.getIntState("growth")
   }

   override fun setGrowth(value: Int): TorchflowerCrop {
       return this.setState("growth", value)
   }
}