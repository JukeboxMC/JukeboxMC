package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.LeafType2

interface Leaves2 : Block {

   fun isUpdate(): Boolean

   fun setUpdate(value: Boolean): Leaves2

   fun getNewLeafType(): LeafType2

   fun setNewLeafType(value: LeafType2): Leaves2

   fun isPersistent(): Boolean

   fun setPersistent(value: Boolean): Leaves2

}