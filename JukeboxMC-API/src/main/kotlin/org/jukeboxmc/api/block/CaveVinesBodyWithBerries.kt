package org.jukeboxmc.api.block

interface CaveVinesBodyWithBerries : Block {

   fun getGrowingPlantAge(): Int

   fun setGrowingPlantAge(value: Int): CaveVinesBodyWithBerries

}