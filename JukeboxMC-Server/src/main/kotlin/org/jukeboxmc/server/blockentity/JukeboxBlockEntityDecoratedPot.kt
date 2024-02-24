package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityDecoratedPot
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityDecoratedPot(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityDecoratedPot {

}