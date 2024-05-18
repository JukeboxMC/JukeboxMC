package org.jukeboxmc.api.block

interface Sapling : Block {

   fun hasAge(): Boolean

   fun setAge(value: Boolean): Sapling

}