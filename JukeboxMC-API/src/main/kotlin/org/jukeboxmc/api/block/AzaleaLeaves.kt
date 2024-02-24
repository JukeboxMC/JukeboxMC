package org.jukeboxmc.api.block

interface AzaleaLeaves : Block {

   fun isUpdate(): Boolean

   fun setUpdate(value: Boolean): AzaleaLeaves

   fun isPersistent(): Boolean

   fun setPersistent(value: Boolean): AzaleaLeaves

}