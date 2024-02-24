package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.BarrelInventory

interface BlockEntityBarrel : BlockEntity {

    fun getBarrelInventory(): BarrelInventory

}