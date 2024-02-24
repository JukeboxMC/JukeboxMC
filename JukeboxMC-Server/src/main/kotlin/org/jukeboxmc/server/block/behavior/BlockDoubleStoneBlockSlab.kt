package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.DoubleStoneBlockSlab
import org.jukeboxmc.api.block.data.StoneSlabType
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.server.block.JukeboxBlock

class BlockDoubleStoneBlockSlab(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    DoubleStoneBlockSlab {

   override fun getVerticalHalf(): VerticalHalf {
       return VerticalHalf.valueOf(this.getStringState("minecraft:vertical_half"))
   }

   override fun setVerticalHalf(value: VerticalHalf): BlockDoubleStoneBlockSlab {
       return this.setState("minecraft:vertical_half", value.name.lowercase())
   }

   override fun getStoneSlabType(): StoneSlabType {
       return StoneSlabType.valueOf(this.getStringState("stone_slab_type"))
   }

   override fun setStoneSlabType(value: StoneSlabType): BlockDoubleStoneBlockSlab {
       return this.setState("stone_slab_type", value.name.lowercase())
   }
}