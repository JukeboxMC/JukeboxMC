package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.Wall
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.block.data.WallConnectionType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.UpdateReason
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

open class BlockWall(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Wall {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        this.updateWall()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun onUpdate(updateReason: UpdateReason): Long {
        if (updateReason == UpdateReason.NEIGHBORS) {
            this.updateWall()
        }
        return super.onUpdate(updateReason)
    }

    private fun updateWall() {
        for (value in Direction.entries) {
            val block: Block = this.getRelative(value.toBlockFace())

            if (canConnect(block)) {
                this.connect(value, WallConnectionType.SHORT)
            } else {
                this.connect(value, WallConnectionType.NONE)
            }
        }

        if (getWallConnectionType(Direction.NORTH) === WallConnectionType.SHORT && getWallConnectionType(Direction.SOUTH) === WallConnectionType.SHORT) {
            this.setWallPost(getWallConnectionType(Direction.WEST) !== WallConnectionType.NONE || getWallConnectionType(
                Direction.EAST) !== WallConnectionType.NONE)
        } else if (getWallConnectionType(Direction.WEST) === WallConnectionType.SHORT && getWallConnectionType(Direction.EAST) === WallConnectionType.SHORT) {
            this.setWallPost(getWallConnectionType(Direction.SOUTH) !== WallConnectionType.NONE || getWallConnectionType(
                Direction.NORTH) !== WallConnectionType.NONE)
        }

        if (this.getRelative(BlockFace.UP).isSolid()) {
            this.setWallPost(true)
        }
        this.update()
        this.getLocation().getLoadedChunk()?.setBlock(this.getLocation(), 0, this)
    }

    private fun canConnect(block: Block): Boolean {
        if (block.getType().isStainedGlassPane() || block.getType().isWall()) {
            return true
        }
        return block.isSolid() && !block.isTransparent()
    }

    private fun connect(direction: Direction?, wallConnectionType: WallConnectionType) {
        when (direction) {
            Direction.SOUTH -> this.setWallConnectionTypeSouth(wallConnectionType)
            Direction.EAST -> this.setWallConnectionTypeEast(wallConnectionType)
            Direction.WEST -> this.setWallConnectionTypeWest(wallConnectionType)
            else -> this.setWallConnectionTypeNorth(wallConnectionType)
        }
    }

    private fun getWallConnectionType(direction: Direction?): WallConnectionType {
        return when (direction) {
            Direction.SOUTH -> this.getWallConnectionTypeSouth()
            Direction.EAST -> this.getWallConnectionTypeEast()
            Direction.WEST -> this.getWallConnectionTypeWest()
            else -> this.getWallConnectionTypeNorth()
        }
    }

   override fun getWallConnectionTypeEast(): WallConnectionType {
       return WallConnectionType.valueOf(this.getStringState("wall_connection_type_east"))
   }

   override fun setWallConnectionTypeEast(value: WallConnectionType): Wall {
       return this.setState("wall_connection_type_east", value.name.lowercase())
   }

   override fun isWallPost(): Boolean {
       return this.getBooleanState("wall_post_bit")
   }

   override fun setWallPost(value: Boolean): Wall {
       return this.setState("wall_post_bit", value.toByte())
   }

   override fun getWallConnectionTypeSouth(): WallConnectionType {
       return WallConnectionType.valueOf(this.getStringState("wall_connection_type_south"))
   }

   override fun setWallConnectionTypeSouth(value: WallConnectionType): Wall {
       return this.setState("wall_connection_type_south", value.name.lowercase())
   }

   override fun getWallConnectionTypeWest(): WallConnectionType {
       return WallConnectionType.valueOf(this.getStringState("wall_connection_type_west"))
   }

   override fun setWallConnectionTypeWest(value: WallConnectionType): Wall {
       return this.setState("wall_connection_type_west", value.name.lowercase())
   }

   override fun getWallConnectionTypeNorth(): WallConnectionType {
       return WallConnectionType.valueOf(this.getStringState("wall_connection_type_north"))
   }

   override fun setWallConnectionTypeNorth(value: WallConnectionType): Wall {
       return this.setState("wall_connection_type_north", value.name.lowercase())
   }
}