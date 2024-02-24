package org.jukeboxmc.api.block

interface Farmland : Block {

   fun getMoisturizedAmount(): Int

   fun setMoisturizedAmount(value: Int): Farmland

}