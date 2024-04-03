package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Cocoa
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockCocoa(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Cocoa {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        this.setDirection(player.getDirection().opposite())
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun getDirection(): Direction {
        return when (this.getIntState("direction")) {
            0 -> Direction.EAST
            1 -> Direction.SOUTH
            2 -> Direction.WEST
            else -> Direction.NORTH
        }
    }

    override fun setDirection(value: Direction): BlockCocoa {
        return when (value) {
            Direction.EAST -> this.setState("direction", 0)
            Direction.SOUTH -> this.setState("direction", 1)
            Direction.WEST -> this.setState("direction", 2)
            else -> this.setState("direction", 3)
        }
    }

    override fun getAge(): Int {
        return this.getIntState("age")
    }

    override fun setAge(value: Int): BlockCocoa {
        return this.setState("age", value)
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return if (this.getAge() != 3) {
            mutableListOf()
        } else {
            mutableListOf(Item.create(ItemType.COCOA_BEANS, 3))
        }
    }
}