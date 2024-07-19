package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockTallgrass(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getDrops(item: Item): MutableList<Item> {
        if (this.getRandom().nextFloat() <= 0.125f) {
            return mutableListOf(Item.create(ItemType.WHEAT_SEEDS))
        }
        return mutableListOf()
    }
}