package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityCommandBlock
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityCommandBlock(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityCommandBlock {

}