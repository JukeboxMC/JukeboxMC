package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.DoubleStoneBlockSlab2
import org.jukeboxmc.api.block.data.StoneSlabType2
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.ToolType
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.behavior.ItemStoneBlockSlab2

class BlockDoubleStoneBlockSlab2(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    DoubleStoneBlockSlab2 {

   override fun getVerticalHalf(): VerticalHalf {
       return VerticalHalf.valueOf(this.getStringState("minecraft:vertical_half"))
   }

   override fun setVerticalHalf(value: VerticalHalf): BlockDoubleStoneBlockSlab2 {
       return this.setState("minecraft:vertical_half", value.name.lowercase())
   }

   override fun getStoneSlabType2(): StoneSlabType2 {
       return StoneSlabType2.valueOf(this.getStringState("stone_slab_type_2"))
   }

   override fun setStoneSlabType2(value: StoneSlabType2): BlockDoubleStoneBlockSlab2 {
       return this.setState("stone_slab_type_2", value.name.lowercase())
   }

    override fun getDrops(item: Item): MutableList<Item> {
        return when (this.getType()) {
            BlockType.DOUBLE_STONE_BLOCK_SLAB2-> {
                this.createItemDrop(item, Item.create<ItemStoneBlockSlab2>(ItemType.STONE_BLOCK_SLAB2).apply {
                    this.setAmount(2)
                    this.setStoneSlabType2(this@BlockDoubleStoneBlockSlab2.getStoneSlabType2())
                }, toolType = ToolType.PICKAXE)
            }
            else -> mutableListOf()
        }
    }

    override fun toItem(): Item {
        return Item.create<ItemStoneBlockSlab2>(ItemType.STONE_BLOCK_SLAB2).apply {
            this.setStoneSlabType2(this@BlockDoubleStoneBlockSlab2.getStoneSlabType2())
        }
    }
}