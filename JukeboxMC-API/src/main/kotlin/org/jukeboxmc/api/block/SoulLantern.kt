package org.jukeboxmc.api.block

interface SoulLantern : Block {

   fun isHanging(): Boolean

   fun setHanging(value: Boolean): SoulLantern

}