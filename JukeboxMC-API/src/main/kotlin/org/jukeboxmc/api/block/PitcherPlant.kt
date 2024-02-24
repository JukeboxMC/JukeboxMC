package org.jukeboxmc.api.block

interface PitcherPlant : Block {

   fun isUpperBlock(): Boolean

   fun setUpperBlock(value: Boolean): PitcherPlant

}