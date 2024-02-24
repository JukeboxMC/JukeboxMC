package org.jukeboxmc.api.event.block

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.player.Player

class BlockBreakEvent(
    private val block: Block,
    private val player: Player,
    private val drops: MutableList<Item>
) : BlockEvent(block), Cancellable {

    fun getPlayer(): Player {
        return this.player
    }

    fun getDrops(): MutableList<Item> {
        return this.drops
    }
}