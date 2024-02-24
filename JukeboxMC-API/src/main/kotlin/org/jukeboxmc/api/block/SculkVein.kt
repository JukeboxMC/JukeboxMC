package org.jukeboxmc.api.block

interface SculkVein : Block {

   fun getMultiFaceDirections(): Int

   fun setMultiFaceDirections(value: Int): SculkVein

}