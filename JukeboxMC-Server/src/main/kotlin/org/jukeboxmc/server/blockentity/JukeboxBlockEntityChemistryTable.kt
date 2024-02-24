package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.blockentity.BlockEntityChemistryTable
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityChemistryTable(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityChemistryTable {

}