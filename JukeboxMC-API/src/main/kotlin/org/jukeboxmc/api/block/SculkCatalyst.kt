package org.jukeboxmc.api.block

interface SculkCatalyst : Block {

   fun isBloom(): Boolean

   fun setBloom(value: Boolean): SculkCatalyst

}