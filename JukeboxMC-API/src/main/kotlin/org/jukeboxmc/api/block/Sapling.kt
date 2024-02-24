package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.SaplingType

interface Sapling : Block {

   fun hasAge(): Boolean

   fun setAge(value: Boolean): Sapling

   fun getSaplingType(): SaplingType

   fun setSaplingType(value: SaplingType): Sapling

}