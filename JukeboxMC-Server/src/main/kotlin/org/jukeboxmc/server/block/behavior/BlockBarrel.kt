package org.jukeboxmc.server.block.behavior

import org.apache.commons.math3.util.FastMath
import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Barrel
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityBarrel
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.extensions.toJukeboxBlockEntity
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockBarrel(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Barrel {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        if (FastMath.abs(player.getX() - getLocation().getX()) < 2 && FastMath.abs(player.getZ() - getLocation().getZ()) < 2) {
            val y = player.getY() + player.getEyeHeight()
            if (y - getLocation().getY() > 2) {
                this.setFacingDirection(BlockFace.UP)
            } else if (getLocation().getY() - y > 0) {
                this.setFacingDirection(BlockFace.DOWN)
            } else {
                this.setFacingDirection(player.getDirection().toBlockFace().opposite())
            }
        } else {
            this.setFacingDirection(player.getDirection().toBlockFace().opposite())
        }
        this.setOpen(false)
        BlockEntity.create<BlockEntityBarrel>(BlockEntityType.BARREL, this)?.spawn()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun interact(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        world.getBlockEntity(this.getLocation())?.toJukeboxBlockEntity()
            ?.interact(player, blockPosition, clickedPosition, blockFace, itemInHand)
        return true
    }

    override fun isOpen(): Boolean {
        return this.getBooleanState("open_bit")
    }

    override fun setOpen(value: Boolean): BlockBarrel {
        return this.setState("open_bit", value.toByte())
    }

    override fun getFacingDirection(): BlockFace {
        return BlockFace.entries[this.getIntState("facing_direction")]
    }

    override fun setFacingDirection(value: BlockFace): BlockBarrel {
        return this.setState("facing_direction", value.ordinal)
    }
}