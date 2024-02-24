package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Orientation

interface Crafter : Block {

   fun isTriggered(): Boolean

   fun setTriggered(value: Boolean): Crafter

   fun getOrientation(): Orientation

   fun setOrientation(value: Orientation): Crafter

   fun isCrafting(): Boolean

   fun setCrafting(value: Boolean): Crafter

}