package org.jukeboxmc.api.block

interface NetherWart : Block {

   fun getAge(): Int

   fun setAge(value: Int): NetherWart

}