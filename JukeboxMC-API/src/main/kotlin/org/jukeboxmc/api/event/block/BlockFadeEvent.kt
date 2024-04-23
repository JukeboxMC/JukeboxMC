package org.jukeboxmc.api.event.block

import org.jukeboxmc.api.block.Block

class BlockFadeEvent(
    block: Block,
    private val newState: Block
) : BlockEvent(block) {

    fun getNewState(): Block {
        return this.newState
    }

}