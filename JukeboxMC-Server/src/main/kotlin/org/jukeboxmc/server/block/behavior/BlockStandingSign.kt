package org.jukeboxmc.server.block.behavior

import org.apache.commons.math3.util.FastMath
import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.StandingSign
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.SignDirection
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockStandingSign(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), StandingSign {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        if (blockFace === BlockFace.UP) {
            this.setGroundSignDirection(
                SignDirection.entries[FastMath.floor(
                    (player.getLocation().getYaw() + 180) * 16 / 360 + 0.5
                ).toInt() and 0x0f]
            )
            world.setBlock(placePosition, this)
        } else {
            val blockWallSign = Block.create(BlockType.SPRUCE_WALL_SIGN) as BlockWallSign
            blockWallSign.setFacingDirection(blockFace)
            world.setBlock(placePosition, blockWallSign)
        }
        BlockEntity.create(BlockEntityType.SIGN, this)?.spawn()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

   override fun getGroundSignDirection(): SignDirection {
       return SignDirection.entries[this.getIntState("ground_sign_direction")]
   }

   override fun setGroundSignDirection(value: SignDirection): StandingSign {
       return this.setState("ground_sign_direction", value.ordinal)
   }
}