package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.ItemType

class ItemCookedSalmon(itemType: ItemType, countNetworkId: Boolean) : JukeboxItemFood(itemType, countNetworkId) {

    override fun getSaturation(): Float {
        return 9.6F
    }

    override fun getHunger(): Int {
        return 6
    }
}