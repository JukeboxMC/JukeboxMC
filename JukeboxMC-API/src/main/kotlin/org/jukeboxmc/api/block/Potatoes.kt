package org.jukeboxmc.api.block

interface Potatoes : Block {

   fun getGrowth(): Int

   fun setGrowth(value: Int): Potatoes

}