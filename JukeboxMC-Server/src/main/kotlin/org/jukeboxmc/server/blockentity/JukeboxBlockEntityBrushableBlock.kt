package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityBrushableBlock
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityBrushableBlock(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityBrushableBlock {

}