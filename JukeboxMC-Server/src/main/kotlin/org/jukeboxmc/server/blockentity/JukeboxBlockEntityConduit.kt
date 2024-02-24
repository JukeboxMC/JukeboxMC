package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityConduit
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityConduit(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityConduit {

}