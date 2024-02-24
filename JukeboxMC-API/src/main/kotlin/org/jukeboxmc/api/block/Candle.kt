package org.jukeboxmc.api.block

interface Candle : Block {

   fun isLit(): Boolean

   fun setLit(value: Boolean): Candle

   fun getCandles(): Int

   fun setCandles(value: Int): Candle

}