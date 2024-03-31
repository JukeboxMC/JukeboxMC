package org.jukeboxmc.api.item

import org.jukeboxmc.api.block.data.StoneSlabType3

interface StoneSlab3 : Item {

    fun getStoneSlabType3(): StoneSlabType3

    fun setStoneSlabType3(stoneSlabType: StoneSlabType3)

}