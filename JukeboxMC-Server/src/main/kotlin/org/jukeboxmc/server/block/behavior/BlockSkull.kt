package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Skull
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntitySkull
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld
import kotlin.math.floor

class BlockSkull(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Skull {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val block = world.getBlock(placePosition)
        if (block is BlockWater && block.getLiquidDepth() == 0) {
            world.setBlock(placePosition, block, 1, false)
        }
        this.setFacingDirection(blockFace)
        world.setBlock(placePosition, this)
        BlockEntity.create<BlockEntitySkull>(BlockEntityType.SKULL, this)?.let {
            it.apply {
                it.setRotation((floor(player.getYaw() * 16 / 360 + 0.5).toInt() and 0xF).toByte())
                it.setSkullType(itemInHand.getMeta().toByte())
                it.spawn()
            }
        }
        return true
    }

    override fun getFacingDirection(): BlockFace {
        return BlockFace.entries[this.getIntState("facing_direction")]
    }

    override fun setFacingDirection(value: BlockFace): Skull {
        return this.setState("facing_direction", value.ordinal)
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}