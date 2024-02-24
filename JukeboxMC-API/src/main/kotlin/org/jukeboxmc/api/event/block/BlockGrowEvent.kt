package org.jukeboxmc.api.event.block

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.event.Cancellable

class BlockGrowEvent(
    block: Block,
    private val newState: Block
) : BlockEvent(block), Cancellable {

    fun getNewState(): Block {
        return this.newState
    }
}