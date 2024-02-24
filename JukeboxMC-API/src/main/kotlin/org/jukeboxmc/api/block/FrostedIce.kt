package org.jukeboxmc.api.block

interface FrostedIce : Block {

   fun getAge(): Int

   fun setAge(value: Int): FrostedIce

}