package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.ItemType

class ItemCookedBeef(itemType: ItemType, countNetworkId: Boolean) : JukeboxItemFood(itemType, countNetworkId) {

    override fun getSaturation(): Float {
        return 12.8F
    }

    override fun getHunger(): Int {
        return 8
    }


}