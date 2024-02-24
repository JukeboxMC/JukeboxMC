package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Color

interface HardStainedGlassPane : Block {

   fun getColor(): Color

   fun setColor(value: Color): HardStainedGlassPane

}