package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityMobSpawner
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityMobSpawner(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityMobSpawner {

}