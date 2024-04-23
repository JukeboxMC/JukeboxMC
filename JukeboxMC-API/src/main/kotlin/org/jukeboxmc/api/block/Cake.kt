package org.jukeboxmc.api.block

interface Cake : Block {

   fun getCounter(): Int

   fun setCounter(value: Int): Cake

}