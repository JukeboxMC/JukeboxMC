package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.inventory.BeaconInventory

interface BlockEntityBeacon : BlockEntity {

    fun getBeaconInventory(): BeaconInventory

}