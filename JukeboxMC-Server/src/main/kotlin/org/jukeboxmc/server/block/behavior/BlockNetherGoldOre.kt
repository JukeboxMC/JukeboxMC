package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.block.JukeboxBlock
import kotlin.random.Random

class BlockNetherGoldOre(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun getDrops(item: Item): MutableList<Item> {
        return this.createItemOreDrop(item, Item.create(ItemType.GOLD_NUGGET), 2 + Random.nextInt(5))
    }

}