package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.ChestInventory

interface BlockEntityChest : BlockEntity {

    fun getChestInventory(): ChestInventory

}