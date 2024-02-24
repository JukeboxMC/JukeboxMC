package org.jukeboxmc.api.block

interface Vine : Block {

   fun getVineDirection(): Int

   fun setVineDirection(value: Int): Vine

}