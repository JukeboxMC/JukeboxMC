package org.jukeboxmc.api.block

interface CandleCake : Block {

   fun isLit(): Boolean

   fun setLit(value: Boolean): CandleCake

}