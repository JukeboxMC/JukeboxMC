package org.jukeboxmc.api.block

interface PitcherCrop : Block {

   fun isUpperBlock(): Boolean

   fun setUpperBlock(value: Boolean): PitcherCrop

   fun getGrowth(): Int

   fun setGrowth(value: Int): PitcherCrop

}