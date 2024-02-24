package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.item.JukeboxItem

class ItemBanner(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId) {

    override fun toBlock(): Block {
        return Block.create(BlockType.STANDING_BANNER)
    }

}