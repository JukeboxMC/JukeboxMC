package org.jukeboxmc.server.block

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.data.BlockFace

class BlockUpdateNormal(
    private val block: Block,
    private val blockFace: BlockFace
) {

    fun getBlock(): Block {
        return this.block
    }

    fun getBlockFace(): BlockFace {
        return this.blockFace
    }

}