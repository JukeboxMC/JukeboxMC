package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityLodestone
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityLodestone(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityLodestone {

}