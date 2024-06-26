package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SculkShrieker
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockSculkShrieker(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    SculkShrieker {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        BlockEntity.create(BlockEntityType.SCULKSHRIEKER, this)?.spawn()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun isActive(): Boolean {
        return this.getBooleanState("active")
    }

    override fun setActive(value: Boolean): SculkShrieker {
        return this.setState("active", value.toByte())
    }

    override fun isCanSummon(): Boolean {
        return this.getBooleanState("can_summon")
    }

    override fun setCanSummon(value: Boolean): SculkShrieker {
        return this.setState("can_summon", value.toByte())
    }
}