package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.FurnaceInventory

interface BlockEntityFurnace : BlockEntity {

    fun getFurnaceInventory(): FurnaceInventory

}