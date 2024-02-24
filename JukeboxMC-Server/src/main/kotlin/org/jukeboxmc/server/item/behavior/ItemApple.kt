package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.ItemType

class ItemApple(itemType: ItemType, countNetworkId: Boolean) : JukeboxItemFood(itemType, countNetworkId) {

    override fun getSaturation(): Float {
        return 2.4F
    }

    override fun getHunger(): Int {
        return 4
    }
}