package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.LiquidType

interface Cauldron : Block {

   fun getFillLevel(): Int

   fun setFillLevel(value: Int): Cauldron

   fun getCauldronLiquid(): LiquidType

   fun setCauldronLiquid(value: LiquidType): Cauldron

}