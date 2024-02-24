package org.jukeboxmc.api.block

interface RedstoneWire : Block {

   fun getRedstoneSignal(): Int

   fun setRedstoneSignal(value: Int): RedstoneWire

}