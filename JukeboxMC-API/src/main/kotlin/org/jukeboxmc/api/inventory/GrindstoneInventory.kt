package org.jukeboxmc.api.inventory

import org.jukeboxmc.api.item.Item

interface GrindstoneInventory : Inventory {

    fun getInput(): Item

    fun getAdditional(): Item

}