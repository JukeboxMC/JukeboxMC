package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityMovingBlock
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityMovingBlock(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityMovingBlock {

}