package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityBell
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityBell(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityBell {

}