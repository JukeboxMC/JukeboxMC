package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityJukebox
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityJukebox(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityJukebox {

}