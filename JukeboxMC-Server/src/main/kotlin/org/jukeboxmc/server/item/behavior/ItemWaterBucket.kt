package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.item.ItemType

class ItemWaterBucket(itemType: ItemType, countNetworkId: Boolean) : ItemBucket(itemType, countNetworkId) {

    override fun toBlock(): Block {
        return Block.create(BlockType.WATER)
    }

}