package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.StoneSlabType4
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.StoneSlab4
import org.jukeboxmc.server.block.behavior.BlockStoneBlockSlab4
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.util.BlockPalette

class ItemStoneBlockSlab4(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId),
    StoneSlab4 {

    private val block: BlockStoneBlockSlab4 = Block.create<BlockStoneBlockSlab4>(BlockType.STONE_BLOCK_SLAB4)

    override fun getStoneSlabType4(): StoneSlabType4 {
        return this.block.getStoneSlabType4()
    }

    override fun setStoneSlabType4(stoneSlabType: StoneSlabType4) {
        val blockNetworkId = this.block.setStoneSlabType4(stoneSlabType).getNetworkId()
        this.setBlockNetworkId(blockNetworkId)
        this.block.setBlockStates(BlockPalette.getBlockNbt(blockNetworkId).getCompound("states"))
    }

}