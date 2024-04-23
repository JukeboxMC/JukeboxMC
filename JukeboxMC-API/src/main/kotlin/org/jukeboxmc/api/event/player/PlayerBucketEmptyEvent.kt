package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.player.Player

class PlayerBucketEmptyEvent(
    player: Player,
    bucket: Item,
    itemInHand: Item,
    clickedBlock: Block,
    private val placedBlock: Block
) : PlayerBucketEvent(player, bucket,
    itemInHand,
    clickedBlock
) {

    fun getPlacedBlock(): Block {
        return this.placedBlock
    }

}