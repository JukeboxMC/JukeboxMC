package org.jukeboxmc.api.block

interface Composter : Block {

   fun getComposterFillLevel(): Int

   fun setComposterFillLevel(value: Int): Composter

}