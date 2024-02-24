package org.jukeboxmc.api.block

interface Water : Block {

   fun getLiquidDepth(): Int

   fun setLiquidDepth(value: Int): Water

}