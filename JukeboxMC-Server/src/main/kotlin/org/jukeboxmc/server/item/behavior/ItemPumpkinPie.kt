package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.ItemType

class ItemPumpkinPie(itemType: ItemType, countNetworkId: Boolean) : JukeboxItemFood(itemType, countNetworkId) {

    override fun getSaturation(): Float {
        return 4.8F
    }

    override fun getHunger(): Int {
        return 8
    }
}