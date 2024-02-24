package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.StructureBlockType

interface StructureBlock : Block {

   fun getStructureBlockType(): StructureBlockType

   fun setStructureBlockType(value: StructureBlockType): StructureBlock

}