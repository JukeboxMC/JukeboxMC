package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.DispenserInventory

interface BlockEntityDispenser : BlockEntity {

    fun getDispenserInventory(): DispenserInventory

}