package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.FurnaceInventory
import org.jukeboxmc.api.item.Item

interface BlockEntityFurnace : BlockEntity {

    fun getFurnaceInventory(): FurnaceInventory

    fun getCookTime(): Int

    fun getBurnTime(): Int

    fun getBurnDuration(): Int

    fun getResult(): Item

}