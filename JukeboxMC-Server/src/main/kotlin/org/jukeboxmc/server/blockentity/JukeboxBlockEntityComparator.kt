package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityComparator
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityComparator(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityComparator {

}