package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.player.Player

open class PlayerBucketEvent(
    player: Player,
    private val bucket: Item,
    private val itemInHand: Item,
    private val clickedBlock: Block
) : PlayerEvent(player), Cancellable {

    fun getBucket(): Item {
        return this.bucket
    }

    fun getItemInHand(): Item {
        return this.itemInHand
    }

    fun getClickedBlock(): Block {
        return this.clickedBlock
    }
}