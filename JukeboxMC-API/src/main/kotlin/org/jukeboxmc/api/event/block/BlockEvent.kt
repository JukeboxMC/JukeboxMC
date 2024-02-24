package org.jukeboxmc.api.event.block

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.event.Event

abstract class BlockEvent(
    private val block: Block
) : Event() {

    fun getBlock(): Block {
        return this.block
    }

}