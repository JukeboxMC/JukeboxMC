package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Seagrass
import org.jukeboxmc.api.block.data.SeaGrassType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockSeagrass(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Seagrass {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getSeaGrassType(): SeaGrassType {
       return SeaGrassType.valueOf(this.getStringState("sea_grass_type"))
   }

   override fun setSeaGrassType(value: SeaGrassType): Seagrass {
       return this.setState("sea_grass_type", value.name.lowercase())
   }
}