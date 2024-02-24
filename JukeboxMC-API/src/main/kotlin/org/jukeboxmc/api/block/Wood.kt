package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Axis
import org.jukeboxmc.api.block.data.WoodType

interface Wood : Block {

    fun getPillarAxis(): Axis

    fun setPillarAxis(value: Axis): Wood

    fun isStripped(): Boolean

    fun setStripped(value: Boolean): Wood

    fun getWoodType(): WoodType

    fun setWoodType(value: WoodType): Wood

}