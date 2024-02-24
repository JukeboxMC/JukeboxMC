package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityCauldron
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityCauldron(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityCauldron {

}