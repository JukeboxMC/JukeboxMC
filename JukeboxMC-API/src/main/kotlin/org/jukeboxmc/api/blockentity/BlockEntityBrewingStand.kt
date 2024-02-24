package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.BrewingStandInventory

interface BlockEntityBrewingStand : BlockEntity {

    fun getBrewingStandInventory(): BrewingStandInventory

}