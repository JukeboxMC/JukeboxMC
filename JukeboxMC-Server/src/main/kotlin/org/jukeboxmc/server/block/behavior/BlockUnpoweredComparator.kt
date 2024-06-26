package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.UnpoweredComparator
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockUnpoweredComparator(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    UnpoweredComparator {

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
        this.setCardinalDirection(player.getDirection().opposite())
        BlockEntity.create(BlockEntityType.COMPARATOR, this)?.spawn()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun isOutputSubtract(): Boolean {
        return this.getBooleanState("output_subtract_bit")
    }

    override fun setOutputSubtract(value: Boolean): UnpoweredComparator {
        return this.setState("output_subtract_bit", value.toByte())
    }

    override fun getCardinalDirection(): Direction {
        return Direction.valueOf(this.getStringState("minecraft:cardinal_direction"))
    }

    override fun setCardinalDirection(value: Direction): UnpoweredComparator {
        return this.setState("minecraft:cardinal_direction", value.name.lowercase())
    }

    override fun isOutputLit(): Boolean {
        return this.getBooleanState("output_lit_bit")
    }

    override fun setOutputLit(value: Boolean): UnpoweredComparator {
        return this.setState("output_lit_bit", value.toByte())
    }

    override fun toItem(): Item {
        return Item.create(ItemType.COMPARATOR)
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}