package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.DripstoneThickness

interface PointedDripstone : Block {

   fun isHanging(): Boolean

   fun setHanging(value: Boolean): PointedDripstone

   fun getDripstoneThickness(): DripstoneThickness

   fun setDripstoneThickness(value: DripstoneThickness): PointedDripstone

}