package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityJigsawBlock
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityJigsawBlock(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityJigsawBlock {

}