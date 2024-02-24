package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityHangingSign
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityHangingSign(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntitySign(blockEntityType, block), BlockEntityHangingSign {

}