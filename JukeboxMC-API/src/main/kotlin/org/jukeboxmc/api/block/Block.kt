package org.jukeboxmc.api.block

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.JukeboxMC
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.TierType
import org.jukeboxmc.api.item.ToolType
import org.jukeboxmc.api.math.AxisAlignedBB
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.world.World
import java.awt.Color

interface Block {

    companion object {

        fun create(blockType: BlockType): Block {
            return JukeboxMC.getServer().createBlock(blockType)
        }

        fun <T> create(blockType: BlockType): T {
           return JukeboxMC.getServer().createBlock(blockType)
        }

        fun create(blockType: BlockType, blockStates: NbtMap): Block {
            return JukeboxMC.getServer().createBlock(blockType, blockStates)
        }

        fun <T> create(blockType: BlockType, blockStates: NbtMap): T {
            return JukeboxMC.getServer().createBlock(blockType, blockStates)
        }
    }

    fun getNetworkId(): Int

    fun getIdentifier(): Identifier

    fun getBlockStates(): NbtMap?

    fun getBoundingBox(): AxisAlignedBB

    fun getType(): BlockType

    fun getWorld(): World

    fun getLocation(): Location

    fun getX(): Float

    fun getY(): Float

    fun getZ(): Float

    fun getBlockX(): Int

    fun getBlockY(): Int

    fun getBlockZ(): Int

    fun getLayer(): Int

    fun getRelative(blockFace: BlockFace): Block

    fun getRelative(blockFace: BlockFace, layer: Int): Block

    fun getRelative(blockFace: BlockFace, layer: Int, step: Int): Block

    fun getRelative(direction: Direction): Block

    fun getRelative(direction: Direction, layer: Int): Block

    fun getRelative(direction: Direction, layer: Int, step: Int): Block

    fun breakNaturally()

    fun getBlockColor(): Color

    fun isSolid(): Boolean

    fun isTransparent(): Boolean

    fun getHardness(): Float

    fun getFriction(): Float

    fun getBlastResistance(): Float

    fun hasBlockEntity(): Boolean

    fun getBlockEntityName(): String

    fun hasCollision(): Boolean

    fun toItem(): Item

    fun getDrops(item: Item): MutableList<Item>

    fun canPassThrough(): Boolean

    fun canBeFlowedInto(): Boolean

    fun getToolType(): ToolType

    fun getTierType(): TierType

    fun clone(): Block

}