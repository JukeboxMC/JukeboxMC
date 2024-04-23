package org.jukeboxmc.api.event.block

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.event.Cancellable

class BlockFromToEvent(
    block: Block,
    private val blockTo: Block
) : BlockEvent(block), Cancellable {

    fun getBlockTo(): Block {
        return this.blockTo
    }

}