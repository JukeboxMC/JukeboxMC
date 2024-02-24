package org.jukeboxmc.api.block

interface MangroveLeaves : Block {

   fun isUpdate(): Boolean

   fun setUpdate(value: Boolean): MangroveLeaves

   fun isPersistent(): Boolean

   fun setPersistent(value: Boolean): MangroveLeaves

}