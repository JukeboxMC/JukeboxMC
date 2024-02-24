package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityNetherReactor
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityNetherReactor(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityNetherReactor {

}