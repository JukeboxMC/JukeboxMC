package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.CoralColor
import org.jukeboxmc.api.item.CoralFan
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.block.behavior.BlockCoralFan
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.util.BlockPalette

class ItemCoralFan(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId), CoralFan {

    private val block: BlockCoralFan = Block.create<BlockCoralFan>(BlockType.CORAL_FAN)

    override fun getColor(): CoralColor {
        return this.block.getCoralColor()
    }

    override fun setColor(coralColor: CoralColor) {
        val blockNetworkId = this.block.setCoralColor(coralColor).getNetworkId()
        this.setBlockNetworkId(blockNetworkId)
        this.block.setBlockStates(BlockPalette.getBlockNbt(blockNetworkId).getCompound("states"))
    }


}