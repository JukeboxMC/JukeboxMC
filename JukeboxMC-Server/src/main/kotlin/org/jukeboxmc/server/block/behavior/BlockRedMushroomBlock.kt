package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.RedMushroomBlock
import org.jukeboxmc.server.block.JukeboxBlock

class BlockRedMushroomBlock(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), RedMushroomBlock {

   override fun getHugeMushrooms(): Int {
       return this.getIntState("huge_mushroom_bits")
   }

   override fun setHugeMushrooms(value: Int): RedMushroomBlock {
       return this.setState("huge_mushroom_bits", value)
   }
}