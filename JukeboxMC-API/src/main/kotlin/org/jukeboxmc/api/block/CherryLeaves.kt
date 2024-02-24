package org.jukeboxmc.api.block

interface CherryLeaves : Block {

   fun isUpdate(): Boolean

   fun setUpdate(value: Boolean): CherryLeaves

   fun isPersistent(): Boolean

   fun setPersistent(value: Boolean): CherryLeaves

}