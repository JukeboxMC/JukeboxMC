package org.jukeboxmc.api.block

interface SoulFire : Block {

   fun getAge(): Int

   fun setAge(value: Int): SoulFire

}