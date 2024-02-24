package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.HangingSign
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.SignDirection
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld
import kotlin.math.floor

class BlockHangingSign(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), HangingSign {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        var face: BlockFace = blockFace
        if (face == BlockFace.UP) {
            val checkBlockFace = checkGroundBlock() ?: return false
            face = checkBlockFace
        }
        val block = world.getBlock(blockPosition)
        if (block is BlockHangingSign && face != BlockFace.DOWN) {
            return false
        }
        if (face == BlockFace.DOWN) {
            this.setHanging(true)
            val signDirection = SignDirection.entries[floor(((player.getYaw() + 180) * 16 / 360) + 0.5).toInt() and 0x0F]
            if (player.isSneaking() || block.getType() == BlockType.CHAIN || block is BlockHangingSign ) {
                this.setAttached(true)
                this.setGroundSignDirection(signDirection)
            } else {
                this.setFacingDirection(signDirection.getClosestBlockFace())
            }
        } else {
            this.setFacingDirection(face.getRightDirection())
        }
        BlockEntity.create(BlockEntityType.HANGINGSIGN, this)?.spawn()
        world.setBlock(placePosition, this)
        player.openSignEditor(this.getLocation(), true)
        return true
    }

    private fun checkGroundBlock(): BlockFace? {
        if (this.getRelative(BlockFace.NORTH).getType() != BlockType.AIR) return BlockFace.NORTH
        if (this.getRelative(BlockFace.SOUTH).getType() != BlockType.AIR) return BlockFace.SOUTH
        if (this.getRelative(BlockFace.WEST).getType() != BlockType.AIR) return BlockFace.WEST
        if (this.getRelative(BlockFace.EAST).getType() != BlockType.AIR) return BlockFace.EAST
        return null
    }

    override fun getGroundSignDirection(): SignDirection {
        return SignDirection.entries[this.getIntState("ground_sign_direction")]
    }

    override fun setGroundSignDirection(value: SignDirection): HangingSign {
        return this.setState("ground_sign_direction", value.ordinal)
    }

    override fun getFacingDirection(): BlockFace {
        return BlockFace.entries[this.getIntState("facing_direction")]
    }

    override fun setFacingDirection(value: BlockFace): HangingSign {
        return this.setState("facing_direction", value.ordinal)
    }

    override fun isAttached(): Boolean {
        return this.getBooleanState("attached_bit")
    }

    override fun setAttached(value: Boolean): HangingSign {
        return this.setState("attached_bit", value.toByte())
    }

    override fun isHanging(): Boolean {
        return this.getBooleanState("hanging")
    }

    override fun setHanging(value: Boolean): HangingSign {
        return this.setState("hanging", value.toByte())
    }
}