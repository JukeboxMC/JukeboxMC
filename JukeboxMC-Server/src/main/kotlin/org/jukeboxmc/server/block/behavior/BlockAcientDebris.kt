package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.server.block.JukeboxBlock

class BlockAcientDebris(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun getDrops(item: Item): MutableList<Item> {
        return this.createItemDrop(item, this.toItem())
    }

}