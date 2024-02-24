package org.jukeboxmc.api.event.block

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.player.Player

class BlockPlaceEvent(
    placedBlock: Block,
    private val replacedBlock: Block,
    private val clickedBlock: Block,
    private val player: Player
) : BlockEvent(placedBlock), Cancellable {

    fun getReplacedBlock(): Block {
        return this.replacedBlock
    }

    fun getClickedBlock(): Block {
        return this.clickedBlock
    }

    fun getPlayer(): Player {
        return this.player
    }

}