package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SculkCatalyst
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockSculkCatalyst(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    SculkCatalyst {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        BlockEntity.create(BlockEntityType.SCULKCATALYST, this)?.spawn()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun isBloom(): Boolean {
        return this.getBooleanState("bloom")
    }

    override fun setBloom(value: Boolean): SculkCatalyst {
        return this.setState("bloom", value.toByte())
    }
}