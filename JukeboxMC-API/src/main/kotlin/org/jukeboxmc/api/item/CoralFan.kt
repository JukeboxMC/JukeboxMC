package org.jukeboxmc.api.item

import org.jukeboxmc.api.block.data.CoralColor

interface CoralFan : Item {

    fun getColor(): CoralColor

    fun setColor(coralColor: CoralColor)

}