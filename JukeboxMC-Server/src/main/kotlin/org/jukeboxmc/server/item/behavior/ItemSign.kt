package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.item.Burnable
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.item.JukeboxItem
import java.time.Duration

class ItemSign(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId), Burnable {

    override fun toBlock(): Block {
        var blockType: BlockType = BlockType.AIR
        when(this.getType()) {
            ItemType.OAK_SIGN -> blockType = BlockType.STANDING_SIGN
            ItemType.SPRUCE_SIGN -> blockType = BlockType.SPRUCE_STANDING_SIGN
            ItemType.BIRCH_SIGN -> blockType = BlockType.BIRCH_STANDING_SIGN
            ItemType.JUNGLE_SIGN -> blockType = BlockType.JUNGLE_STANDING_SIGN
            ItemType.ACACIA_SIGN -> blockType = BlockType.ACACIA_STANDING_SIGN
            ItemType.DARK_OAK_SIGN -> blockType = BlockType.DARKOAK_STANDING_SIGN
            ItemType.MANGROVE_SIGN -> blockType = BlockType.MANGROVE_STANDING_SIGN
            ItemType.CHERRY_SIGN -> blockType = BlockType.CHERRY_STANDING_SIGN
            ItemType.BAMBOO_SIGN -> blockType = BlockType.BAMBOO_STANDING_SIGN
            ItemType.CRIMSON_SIGN -> blockType = BlockType.CRIMSON_STANDING_SIGN
            ItemType.WARPED_SIGN -> blockType = BlockType.WARPED_STANDING_SIGN
            else -> {}
        }
        return Block.create(blockType)
    }

    override fun getBurnTime(): Duration {
        return Duration.ofMillis(200)
    }

}