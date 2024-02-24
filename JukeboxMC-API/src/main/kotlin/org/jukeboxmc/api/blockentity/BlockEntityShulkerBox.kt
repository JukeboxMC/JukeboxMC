package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.ShulkerBoxInventory

interface BlockEntityShulkerBox : BlockEntity {

    fun getFacing(): Byte

    fun setFacing(facing: Byte)

    fun isUndyed(): Boolean

    fun setUndyed(undyed: Boolean)

    fun getShulkerBoxInventory(): ShulkerBoxInventory

}