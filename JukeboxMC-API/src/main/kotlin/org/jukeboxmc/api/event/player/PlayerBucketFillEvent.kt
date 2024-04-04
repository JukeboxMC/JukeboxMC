package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.player.Player

class PlayerBucketFillEvent(
    player: Player,
    bucket: Item,
    itemInHand: Item,
    clickedBlock: Block
) : PlayerBucketEvent(player, bucket,
    itemInHand,
    clickedBlock
){

}