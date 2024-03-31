package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.StoneSlabType2
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.StoneSlab2
import org.jukeboxmc.server.block.behavior.BlockStoneBlockSlab2
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.util.BlockPalette

class ItemStoneBlockSlab2(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId),
    StoneSlab2 {

    private val block: BlockStoneBlockSlab2 = Block.create<BlockStoneBlockSlab2>(BlockType.STONE_BLOCK_SLAB2)

    override fun getStoneSlabType2(): StoneSlabType2 {
        return this.block.getStoneSlabType2()
    }

    override fun setStoneSlabType2(stoneSlabType: StoneSlabType2) {
        val blockNetworkId = this.block.setStoneSlabType2(stoneSlabType).getNetworkId()
        this.setBlockNetworkId(blockNetworkId)
        this.block.setBlockStates(BlockPalette.getBlockNbt(blockNetworkId).getCompound("states"))
    }

}