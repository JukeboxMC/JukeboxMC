package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.ItemType

class ItemDriedKelp(itemType: ItemType, countNetworkId: Boolean) : JukeboxItemFood(itemType, countNetworkId) {

    override fun getSaturation(): Float {
        return 0.2F
    }

    override fun getHunger(): Int {
        return 1
    }
}