package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.ItemType

class ItemGoldenCarrot(itemType: ItemType, countNetworkId: Boolean) : JukeboxItemFood(itemType, countNetworkId) {

    override fun getSaturation(): Float {
        return 14.4F
    }

    override fun getHunger(): Int {
        return 6
    }
}