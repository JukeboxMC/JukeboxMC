package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntitySculkSensor
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntitySculkSensor(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntitySculkSensor {

}