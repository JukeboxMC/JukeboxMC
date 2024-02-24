package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityDaylightDetector
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityDaylightDetector(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityDaylightDetector {

}