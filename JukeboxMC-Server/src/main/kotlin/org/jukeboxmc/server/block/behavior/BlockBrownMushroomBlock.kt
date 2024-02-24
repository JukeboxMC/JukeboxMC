package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BrownMushroomBlock
import org.jukeboxmc.server.block.JukeboxBlock

class BlockBrownMushroomBlock(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    BrownMushroomBlock {

   override fun getHugeMushrooms(): Int {
       return this.getIntState("huge_mushroom_bits")
   }

   override fun setHugeMushrooms(value: Int): BlockBrownMushroomBlock {
       return this.setState("huge_mushroom_bits", value)
   }
}