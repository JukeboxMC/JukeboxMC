package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityMusic
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityMusic(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityMusic {

}