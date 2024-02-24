package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.block.data.Color

interface BlockEntityBed : BlockEntity {

    fun getColor(): Color

    fun setColor(color: Color)

}