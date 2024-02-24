package org.jukeboxmc.api.block

interface SuspiciousGravel : Block {

   fun isHanging(): Boolean

   fun setHanging(value: Boolean): SuspiciousGravel

   fun getBrushedProgress(): Int

   fun setBrushedProgress(value: Int): SuspiciousGravel

}