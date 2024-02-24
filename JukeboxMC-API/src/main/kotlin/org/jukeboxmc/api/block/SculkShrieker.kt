package org.jukeboxmc.api.block

interface SculkShrieker : Block {

   fun isActive(): Boolean

   fun setActive(value: Boolean): SculkShrieker

   fun isCanSummon(): Boolean

   fun setCanSummon(value: Boolean): SculkShrieker

}