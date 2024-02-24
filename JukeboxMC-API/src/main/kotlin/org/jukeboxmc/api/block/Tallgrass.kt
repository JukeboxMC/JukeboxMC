package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.TallGrassType

interface Tallgrass : Block {

   fun getTallGrassType(): TallGrassType

   fun setTallGrassType(value: TallGrassType): Tallgrass

}