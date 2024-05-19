package org.jukeboxmc.api.inventory

import org.jukeboxmc.api.item.Item

interface LoomInventory : Inventory {

    fun getBanner(): Item

    fun getMaterial(): Item

    fun getPattern(): Item

}