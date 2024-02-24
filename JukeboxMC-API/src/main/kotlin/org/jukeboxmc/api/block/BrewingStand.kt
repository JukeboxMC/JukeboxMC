package org.jukeboxmc.api.block

interface BrewingStand : Block {

   fun isBrewingStandSlotC(): Boolean

   fun setBrewingStandSlotC(value: Boolean): BrewingStand

   fun isBrewingStandSlotA(): Boolean

   fun setBrewingStandSlotA(value: Boolean): BrewingStand

   fun isBrewingStandSlotB(): Boolean

   fun setBrewingStandSlotB(value: Boolean): BrewingStand

}