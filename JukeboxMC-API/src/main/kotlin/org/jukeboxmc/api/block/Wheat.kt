package org.jukeboxmc.api.block

interface Wheat : Block {

   fun getGrowth(): Int

   fun setGrowth(value: Int): Wheat

}