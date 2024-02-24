package org.jukeboxmc.api.block

interface DaylightDetectorInverted : Block {

   fun getRedstoneSignal(): Int

   fun setRedstoneSignal(value: Int): DaylightDetectorInverted

}