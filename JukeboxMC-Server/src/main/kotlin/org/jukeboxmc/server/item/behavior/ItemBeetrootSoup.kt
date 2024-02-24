package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.ItemType

class ItemBeetrootSoup(itemType: ItemType, countNetworkId: Boolean) : JukeboxItemFood(itemType, countNetworkId) {

    override fun getSaturation(): Float {
        return 7.2F
    }

    override fun getHunger(): Int {
        return 6
    }

}