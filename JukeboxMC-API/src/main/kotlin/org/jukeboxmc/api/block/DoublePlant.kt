package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.PlantType

interface DoublePlant : Block {

   fun isUpperBlock(): Boolean

   fun setUpperBlock(value: Boolean): DoublePlant

   fun getDoublePlantType(): PlantType

   fun setDoublePlantType(value: PlantType): DoublePlant

}