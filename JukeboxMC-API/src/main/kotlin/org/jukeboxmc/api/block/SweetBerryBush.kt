package org.jukeboxmc.api.block

interface SweetBerryBush : Block {

   fun getGrowth(): Int

   fun setGrowth(value: Int): SweetBerryBush

}