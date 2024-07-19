package org.jukeboxmc.api.block

interface DoublePlant : Block {

   fun isUpperBlock(): Boolean

   fun setUpperBlock(value: Boolean): DoublePlant

}