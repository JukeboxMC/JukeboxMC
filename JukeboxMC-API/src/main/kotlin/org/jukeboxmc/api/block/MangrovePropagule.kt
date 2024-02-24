package org.jukeboxmc.api.block

interface MangrovePropagule : Block {

   fun isHanging(): Boolean

   fun setHanging(value: Boolean): MangrovePropagule

   fun getPropaguleStage(): Int

   fun setPropaguleStage(value: Int): MangrovePropagule

}