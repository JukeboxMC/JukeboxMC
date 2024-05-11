package org.jukeboxmc.api.inventory

import org.jukeboxmc.api.item.Item

interface SmithingTableInventory : Inventory {

    fun getBase(): Item

    fun getAddition(): Item

    fun getTemplate(): Item

}