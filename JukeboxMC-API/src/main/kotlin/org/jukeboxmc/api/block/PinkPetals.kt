package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface PinkPetals : Block {

   fun getGrowth(): Int

   fun setGrowth(value: Int): PinkPetals

   fun getCardinalDirection(): Direction

   fun setCardinalDirection(value: Direction): PinkPetals

}