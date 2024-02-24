package org.jukeboxmc.api.block

interface FlowingLava : Block {

   fun getLiquidDepth(): Int

   fun setLiquidDepth(value: Int): FlowingLava

}