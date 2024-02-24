package org.jukeboxmc.api.block

interface CherrySapling : Block {

   fun hasAge(): Boolean

   fun setAge(value: Boolean): CherrySapling

}