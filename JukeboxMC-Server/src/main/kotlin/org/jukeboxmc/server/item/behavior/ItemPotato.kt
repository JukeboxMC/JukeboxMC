package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.ItemType

class ItemPotato(itemType: ItemType, countNetworkId: Boolean) : JukeboxItemFood(itemType, countNetworkId) {

    override fun getSaturation(): Float {
        return 0.6F
    }

    override fun getHunger(): Int {
        return 1
    }
}