package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityCount
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityCount(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityCount {

}