package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.ToolType
import org.jukeboxmc.server.item.JukeboxItem

class ItemShears(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId) {

    override fun getToolType(): ToolType {
        return ToolType.SHEARS
    }
}