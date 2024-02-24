package org.jukeboxmc.api.block

interface Tnt : Block {

   fun isExplode(): Boolean

   fun setExplode(value: Boolean): Tnt

   fun isAllowUnderwater(): Boolean

   fun setAllowUnderwater(value: Boolean): Tnt

}