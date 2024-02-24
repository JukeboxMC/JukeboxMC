package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.DropperInventory

interface BlockEntityDropper : BlockEntity {

    fun getDropperInventory(): DropperInventory

}