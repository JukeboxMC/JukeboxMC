package org.jukeboxmc.api.blockentity

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.block.data.Color

interface BlockEntityBanner : BlockEntity {

    fun getColor(): Color

    fun setColor(color: Color)

    fun getType(): Int

    fun setType(type: Int)

    fun getPattern(): MutableList<NbtMap>

}