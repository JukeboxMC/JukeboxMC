package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.LeafType

interface Leaves : Block {

   fun isUpdate(): Boolean

   fun setUpdate(value: Boolean): Leaves

   fun isPersistent(): Boolean

   fun setPersistent(value: Boolean): Leaves

   fun getOldLeafType(): LeafType

   fun setOldLeafType(value: LeafType): Leaves

}