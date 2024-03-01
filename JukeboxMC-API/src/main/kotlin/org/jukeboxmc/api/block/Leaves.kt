package org.jukeboxmc.api.block

interface Leaves : Block {

   fun isUpdate(): Boolean

   fun setUpdate(value: Boolean): Leaves

   fun isPersistent(): Boolean

   fun setPersistent(value: Boolean): Leaves

}