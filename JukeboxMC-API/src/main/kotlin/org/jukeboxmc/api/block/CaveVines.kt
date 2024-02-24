package org.jukeboxmc.api.block

interface CaveVines : Block {

   fun getGrowingPlantAge(): Int

   fun setGrowingPlantAge(value: Int): CaveVines

}