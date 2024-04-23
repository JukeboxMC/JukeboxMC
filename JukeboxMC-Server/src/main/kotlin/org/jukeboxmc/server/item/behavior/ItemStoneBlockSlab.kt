package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.StoneSlabType
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.StoneSlab
import org.jukeboxmc.server.block.behavior.BlockStoneBlockSlab
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.util.BlockPalette

class ItemStoneBlockSlab(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId),
    StoneSlab {

    private val block: BlockStoneBlockSlab = Block.create<BlockStoneBlockSlab>(BlockType.STONE_BLOCK_SLAB)

    override fun getStoneSlabType(): StoneSlabType {
        return this.block.getStoneSlabType()
    }

    override fun setStoneSlabType(stoneSlabType: StoneSlabType) {
        val blockNetworkId = this.block.setStoneSlabType(stoneSlabType).getNetworkId()
        this.setBlockNetworkId(blockNetworkId)
        this.block.setBlockStates(BlockPalette.getBlockNbt(blockNetworkId).getCompound("states"))
    }

}