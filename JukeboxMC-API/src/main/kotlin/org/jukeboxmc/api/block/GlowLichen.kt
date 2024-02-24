package org.jukeboxmc.api.block

interface GlowLichen : Block {

   fun getMultiFaceDirections(): Int

   fun setMultiFaceDirections(value: Int): GlowLichen

}