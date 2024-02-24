package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.SaplingType

interface BambooSapling : Block {

   fun hasAge(): Boolean

   fun setAge(value: Boolean): BambooSapling

   fun getSaplingType(): SaplingType

   fun setSaplingType(value: SaplingType): BambooSapling

}