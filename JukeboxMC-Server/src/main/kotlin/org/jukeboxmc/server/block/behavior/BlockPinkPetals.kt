package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.PinkPetals
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.isOneOf
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockPinkPetals(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    PinkPetals {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val blockDown = world.getBlock(placePosition).getRelative(BlockFace.DOWN)
        if (!blockDown.getType().isOneOf(
                BlockType.GRASS,
                BlockType.DIRT,
                BlockType.FARMLAND,
                BlockType.PODZOL,
                BlockType.DIRT_WITH_ROOTS,
                BlockType.MOSS_BLOCK
            )
        ) return false
        this.setCardinalDirection(player.getDirection().opposite())
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun getGrowth(): Int {
        return this.getIntState("growth")
    }

    override fun setGrowth(value: Int): PinkPetals {
        return this.setState("growth", value)
    }

    override fun getCardinalDirection(): Direction {
        return Direction.valueOf(this.getStringState("minecraft:cardinal_direction"))
    }

    override fun setCardinalDirection(value: Direction): PinkPetals {
        return this.setState("minecraft:cardinal_direction", value.name.lowercase())
    }
}