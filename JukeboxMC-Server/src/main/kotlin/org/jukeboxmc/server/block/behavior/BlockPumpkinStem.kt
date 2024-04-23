package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.PumpkinStem
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockPumpkinStem(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    PumpkinStem {

    override fun canBeReplaced(block: JukeboxBlock): Boolean {
        return true
    }

    override fun getFacingDirection(): BlockFace {
        return BlockFace.entries[this.getIntState("facing_direction")]
    }

    override fun setFacingDirection(value: BlockFace): PumpkinStem {
        return this.setState("facing_direction", value.ordinal)
    }

    override fun getGrowth(): Int {
        return this.getIntState("growth")
    }

    override fun setGrowth(value: Int): PumpkinStem {
        return this.setState("growth", value)
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return this.createItemStemDrop(Item.create(ItemType.PUMPKIN_SEEDS), this.getGrowth())
    }
}