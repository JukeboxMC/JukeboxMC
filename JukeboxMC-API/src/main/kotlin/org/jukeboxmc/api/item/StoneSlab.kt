package org.jukeboxmc.api.item

import org.jukeboxmc.api.block.data.StoneSlabType

interface StoneSlab : Item {

    fun getStoneSlabType(): StoneSlabType

    fun setStoneSlabType(stoneSlabType: StoneSlabType)
}