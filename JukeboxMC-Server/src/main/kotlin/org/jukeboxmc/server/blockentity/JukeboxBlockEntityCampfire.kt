package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityCampfire
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityCampfire(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityCampfire {

}