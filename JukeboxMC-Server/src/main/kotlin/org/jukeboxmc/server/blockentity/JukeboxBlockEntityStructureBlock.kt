package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityStructureBlock
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityStructureBlock(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityStructureBlock {

}