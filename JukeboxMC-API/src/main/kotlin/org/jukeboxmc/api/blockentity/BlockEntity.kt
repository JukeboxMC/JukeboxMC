package org.jukeboxmc.api.blockentity

import org.jukeboxmc.api.JukeboxMC
import org.jukeboxmc.api.block.Block

interface BlockEntity {

    companion object {

        fun create(blockEntityType: BlockEntityType, block: Block): BlockEntity? {
            return JukeboxMC.getServer().createBlockEntity(blockEntityType, block)
        }

        fun <T> create(blockEntityType: BlockEntityType, block: Block): T? {
            return JukeboxMC.getServer().createBlockEntity(blockEntityType, block)
        }

    }

    fun getBlockEntityType(): BlockEntityType

    fun getBlock(): Block

    fun isSpawned(): Boolean

    fun spawn()

}