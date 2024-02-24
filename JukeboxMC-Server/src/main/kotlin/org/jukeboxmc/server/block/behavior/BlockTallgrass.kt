package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Tallgrass
import org.jukeboxmc.api.block.data.TallGrassType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockTallgrass(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Tallgrass {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getTallGrassType(): TallGrassType {
       return TallGrassType.valueOf(this.getStringState("tall_grass_type"))
   }

   override fun setTallGrassType(value: TallGrassType): Tallgrass {
       return this.setState("tall_grass_type", value.name.lowercase())
   }
}