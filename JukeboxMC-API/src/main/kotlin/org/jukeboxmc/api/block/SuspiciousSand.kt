package org.jukeboxmc.api.block

interface SuspiciousSand : Block {

   fun isHanging(): Boolean

   fun setHanging(value: Boolean): SuspiciousSand

   fun getBrushedProgress(): Int

   fun setBrushedProgress(value: Int): SuspiciousSand

}