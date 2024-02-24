package org.jukeboxmc.api.block

interface Lava : Block {

   fun getLiquidDepth(): Int

   fun setLiquidDepth(value: Int): Lava

}