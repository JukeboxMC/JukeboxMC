package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.ToolType
import org.jukeboxmc.server.block.JukeboxBlock
import kotlin.random.Random

class BlockDeadBush(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getDrops(item: Item): MutableList<Item> {
        if (item.getToolType() == ToolType.SHEARS) {
            return mutableListOf(this.toItem())
        }
        return mutableListOf(Item.create(ItemType.STICK, Random.nextInt(3)))
    }
}