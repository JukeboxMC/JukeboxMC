package org.jukeboxmc.api.block

interface Beetroot : Block {

   fun getGrowth(): Int

   fun setGrowth(value: Int): Beetroot

}