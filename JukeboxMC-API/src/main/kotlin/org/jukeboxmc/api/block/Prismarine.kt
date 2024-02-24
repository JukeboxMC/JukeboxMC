package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.PrismarineType

interface Prismarine : Block {

   fun getPrismarineBlockType(): PrismarineType

   fun setPrismarineBlockType(value: PrismarineType): Prismarine

}