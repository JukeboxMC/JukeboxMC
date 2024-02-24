package org.jukeboxmc.api.block

interface DaylightDetector : Block {

   fun getRedstoneSignal(): Int

   fun setRedstoneSignal(value: Int): DaylightDetector

}