package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.SmokerInventory

interface BlockEntitySmoker : BlockEntity {

    fun getSmokerInventory(): SmokerInventory

}