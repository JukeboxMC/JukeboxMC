package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.SeaGrassType

interface Seagrass : Block {

   fun getSeaGrassType(): SeaGrassType

   fun setSeaGrassType(value: SeaGrassType): Seagrass

}