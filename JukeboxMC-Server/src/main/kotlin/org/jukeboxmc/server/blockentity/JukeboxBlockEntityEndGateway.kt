package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityEndGateway
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityEndGateway(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityEndGateway {

}