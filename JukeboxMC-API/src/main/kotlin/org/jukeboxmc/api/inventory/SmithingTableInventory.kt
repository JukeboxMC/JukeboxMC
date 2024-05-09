package org.jukeboxmc.api.inventory

import org.jukeboxmc.api.item.Item

interface SmithingTableInventory : Inventory {

    fun getArmor(): Item

    fun getMaterial(): Item

    fun getTemplate(): Item

}