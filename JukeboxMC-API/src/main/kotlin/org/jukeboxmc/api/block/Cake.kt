package org.jukeboxmc.api.block

interface Cake : Block {

   fun geteCounter(): Int

   fun seteCounter(value: Int): Cake

}