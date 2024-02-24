package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.BlastFurnaceInventory

interface BlockEntityBlastFurnace : BlockEntity {

    fun getBlastFurnaceInventory(): BlastFurnaceInventory

}