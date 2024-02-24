package org.jukeboxmc.api.block

interface SnowLayer : Block {

   fun isCovered(): Boolean

   fun setCovered(value: Boolean): SnowLayer

   fun getHeight(): Int

   fun setHeight(value: Int): SnowLayer

}