package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityPistonArm
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityPistonArm(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityPistonArm {

}