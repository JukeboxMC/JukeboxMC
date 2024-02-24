package org.jukeboxmc.api.block

interface Lantern : Block {

   fun isHanging(): Boolean

   fun setHanging(value: Boolean): Lantern

}