package org.jukeboxmc.api.block

interface FlowingWater : Block {

   fun getLiquidDepth(): Int

   fun setLiquidDepth(value: Int): FlowingWater

}