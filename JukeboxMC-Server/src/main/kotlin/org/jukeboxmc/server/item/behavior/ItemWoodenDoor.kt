package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.item.Burnable
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.item.JukeboxItem
import java.time.Duration

class ItemWoodenDoor(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId),
    Burnable {

    override fun getBurnTime(): Duration {
        return Duration.ofMillis(200)
    }

    override fun toBlock(): Block {
        return Block.create(BlockType.valueOf(this.getType().name))
    }

}