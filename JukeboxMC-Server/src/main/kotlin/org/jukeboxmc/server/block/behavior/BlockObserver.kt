package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Observer
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld
import kotlin.math.abs

class BlockObserver(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Observer {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        if (abs(player.getBlockX() - this.getX()) <= 1 && abs(player.getBlockZ() - this.getZ()) <= 1) {
            val y = player.getY() + player.getEyeHeight()
            if (y - this.getY() > 2) {
                this.setFacingDirection(BlockFace.DOWN)
            } else if (this.getY() - y > 0) {
                this.setFacingDirection(BlockFace.UP)
            } else {
                this.setFacingDirection(player.getDirection().toBlockFace())
            }
        } else {
            this.setFacingDirection(player.getDirection().toBlockFace())
        }
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun isPowered(): Boolean {
        return this.getBooleanState("powered_bit")
    }

    override fun setPowered(value: Boolean): Observer {
        return this.setState("powered_bit", value.toByte())
    }

    override fun getFacingDirection(): BlockFace {
        return BlockFace.valueOf(this.getStringState("minecraft:facing_direction"))
    }

    override fun setFacingDirection(value: BlockFace): Observer {
        return this.setState("minecraft:facing_direction", value.name.lowercase())
    }
}