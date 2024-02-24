package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.block.data.Color

interface BlockEntityBanner : BlockEntity {

    fun getColor(): Color

    fun setColor(color: Color)

    fun getType(): Int

    fun setType(type: Int)

}