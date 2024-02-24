package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityFlowerPot
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityFlowerPot(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityFlowerPot {

}