package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BambooLeafSize
import org.jukeboxmc.api.block.data.BambooStalkThickness

interface Bamboo : Block {

   fun hasAge(): Boolean

   fun setAge(value: Boolean): Bamboo

   fun getBambooStalkThickness(): BambooStalkThickness

   fun setBambooStalkThickness(value: BambooStalkThickness): Bamboo

   fun getBambooLeafSize(): BambooLeafSize

   fun setBambooLeafSize(value: BambooLeafSize): Bamboo

}