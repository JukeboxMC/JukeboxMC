package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Seagrass
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.SeaGrassType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockSeagrass(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Seagrass {

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
            return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
        }
        return false
    }

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getSeaGrassType(): SeaGrassType {
        return SeaGrassType.valueOf(this.getStringState("sea_grass_type"))
    }

    override fun setSeaGrassType(value: SeaGrassType): Seagrass {
        return this.setState("sea_grass_type", value.name.lowercase())
    }

    override fun getWaterLoggingLevel(): Int {
        return 2
    }
}