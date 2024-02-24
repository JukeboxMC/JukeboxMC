package org.jukeboxmc.api.block

interface Carrots : Block {

   fun getGrowth(): Int

   fun setGrowth(value: Int): Carrots

}