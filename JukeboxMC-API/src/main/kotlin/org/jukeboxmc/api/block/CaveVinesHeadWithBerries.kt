package org.jukeboxmc.api.block

interface CaveVinesHeadWithBerries : Block {

   fun getGrowingPlantAge(): Int

   fun setGrowingPlantAge(value: Int): CaveVinesHeadWithBerries

}