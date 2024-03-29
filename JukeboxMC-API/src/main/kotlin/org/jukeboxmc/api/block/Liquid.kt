package org.jukeboxmc.api.block

interface Liquid : Block {

    fun getLiquidDepth(): Int

    fun setLiquidDepth(value: Int): Liquid
}