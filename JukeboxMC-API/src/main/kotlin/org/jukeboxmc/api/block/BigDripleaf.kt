package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BigDripleafTilt
import org.jukeboxmc.api.block.data.Direction

interface BigDripleaf : Block {

   fun isBigDripleafHead(): Boolean

   fun setBigDripleafHead(value: Boolean): BigDripleaf

   fun getBigDripleafTilt(): BigDripleafTilt

   fun setBigDripleafTilt(value: BigDripleafTilt): BigDripleaf

   fun getCardinalDirection(): Direction

   fun setCardinalDirection(value: Direction): BigDripleaf

}