package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityItemFrame
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityItemFrame(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityItemFrame {

}