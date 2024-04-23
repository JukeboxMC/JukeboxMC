package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.StoneSlabType3
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.StoneSlab3
import org.jukeboxmc.server.block.behavior.BlockStoneBlockSlab3
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.util.BlockPalette

class ItemStoneBlockSlab3(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId),
    StoneSlab3 {

    private val block: BlockStoneBlockSlab3 = Block.create<BlockStoneBlockSlab3>(BlockType.STONE_BLOCK_SLAB3)

    override fun getStoneSlabType3(): StoneSlabType3 {
        return this.block.getStoneSlabType3()
    }

    override fun setStoneSlabType3(stoneSlabType: StoneSlabType3) {
        val blockNetworkId = this.block.setStoneSlabType3(stoneSlabType).getNetworkId()
        this.setBlockNetworkId(blockNetworkId)
        this.block.setBlockStates(BlockPalette.getBlockNbt(blockNetworkId).getCompound("states"))
    }

}