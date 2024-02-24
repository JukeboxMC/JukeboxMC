package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityEndPortal
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityEndPortal(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityEndPortal {

}