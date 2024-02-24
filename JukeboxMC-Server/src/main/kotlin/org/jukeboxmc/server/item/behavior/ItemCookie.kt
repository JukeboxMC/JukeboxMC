package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.ItemType

class ItemCookie(itemType: ItemType, countNetworkId: Boolean) : JukeboxItemFood(itemType, countNetworkId) {

    override fun getSaturation(): Float {
        return 0.4F
    }

    override fun getHunger(): Int {
        return 2
    }
}