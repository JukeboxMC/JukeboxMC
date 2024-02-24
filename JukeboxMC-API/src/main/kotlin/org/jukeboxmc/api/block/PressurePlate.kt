package org.jukeboxmc.api.block

interface PressurePlate : Block {

   fun getRedstoneSignal(): Int

   fun setRedstoneSignal(value: Int): PressurePlate

}