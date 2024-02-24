package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityBeehive
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityBeehive(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityBeehive {

}