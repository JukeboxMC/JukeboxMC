package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.ToolType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockWeb(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return if (item.getToolType() == ToolType.SHEARS) {
            mutableListOf(this.toItem())
        } else if (item.getToolType() == ToolType.SWORD) {
            mutableListOf(Item.create(ItemType.STRING, 1))
        } else {
            mutableListOf()
        }
    }
}