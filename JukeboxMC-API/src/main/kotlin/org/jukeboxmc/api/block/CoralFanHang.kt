package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface CoralFanHang : Block {

   fun getCoralDirection(): Direction

   fun setCoralDirection(value: Direction): CoralFanHang

   fun isCoralHangType(): Boolean

   fun setCoralHangType(value: Boolean): CoralFanHang

   fun isDead(): Boolean

   fun setDead(value: Boolean): CoralFanHang

}