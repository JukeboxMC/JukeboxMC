package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.WallSign
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem

class BlockWallSign(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), WallSign {

    override fun getFacingDirection(): BlockFace {
        return BlockFace.entries[this.getIntState("facing_direction")]
    }

    override fun setFacingDirection(value: BlockFace): WallSign {
        return this.setState("facing_direction", value.ordinal)
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return this.createItemDrop(item, this.toItem())
    }

    override fun toItem(): Item {
        return when (this.getType()) {
            BlockType.WALL_SIGN -> Item.create(ItemType.OAK_SIGN)
            BlockType.SPRUCE_WALL_SIGN -> Item.create(ItemType.SPRUCE_SIGN)
            BlockType.BIRCH_WALL_SIGN -> Item.create(ItemType.BIRCH_SIGN)
            BlockType.JUNGLE_WALL_SIGN -> Item.create(ItemType.JUNGLE_SIGN)
            BlockType.ACACIA_WALL_SIGN -> Item.create(ItemType.ACACIA_SIGN)
            BlockType.DARKOAK_WALL_SIGN -> Item.create(ItemType.DARK_OAK_SIGN)
            BlockType.MANGROVE_WALL_SIGN -> Item.create(ItemType.MANGROVE_SIGN)
            BlockType.CHERRY_WALL_SIGN -> Item.create(ItemType.CHERRY_SIGN)
            BlockType.BAMBOO_WALL_SIGN -> Item.create(ItemType.BAMBOO_SIGN)
            BlockType.CRIMSON_WALL_SIGN -> Item.create(ItemType.CRIMSON_SIGN)
            BlockType.WARPED_WALL_SIGN -> Item.create(ItemType.WARPED_SIGN)
            else -> JukeboxItem.AIR
        }
    }
}