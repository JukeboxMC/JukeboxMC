package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.StructureVoidType

interface StructureVoid : Block {

   fun getStructureVoidType(): StructureVoidType

   fun setStructureVoidType(value: StructureVoidType): StructureVoid

}