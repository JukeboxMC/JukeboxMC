package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.BlastFurnaceInventory
import org.jukeboxmc.api.item.Item

interface BlockEntityBlastFurnace : BlockEntity {

    fun getBlastFurnaceInventory(): BlastFurnaceInventory

    fun getCookTime(): Int

    fun getBurnTime(): Int

    fun getBurnDuration(): Int

    fun getResult(): Item

}