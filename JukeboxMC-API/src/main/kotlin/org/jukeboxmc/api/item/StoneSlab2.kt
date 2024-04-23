package org.jukeboxmc.api.item

import org.jukeboxmc.api.block.data.StoneSlabType2

interface StoneSlab2 : Item {

    fun getStoneSlabType2(): StoneSlabType2

    fun setStoneSlabType2(stoneSlabType: StoneSlabType2)

}