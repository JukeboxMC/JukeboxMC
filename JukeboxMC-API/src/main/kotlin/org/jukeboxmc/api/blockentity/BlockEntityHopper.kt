package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.HopperInventory

interface BlockEntityHopper : BlockEntity {

    fun getHopperInventory(): HopperInventory

}