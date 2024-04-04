package org.jukeboxmc.api.event.block

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.Liquid
import org.jukeboxmc.api.event.Cancellable

class LiquidFlowEvent(
    block: Block,
    private val source: Liquid,
    private val newFlowDecay: Int
) : BlockEvent(block), Cancellable {

    fun getSource(): Liquid {
        return this.source
    }

    fun getNewFlowDecay(): Int {
        return this.newFlowDecay
    }


}