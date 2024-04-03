package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.DoubleStoneBlockSlab4
import org.jukeboxmc.api.block.data.StoneSlabType4
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.behavior.ItemStoneBlockSlab4

class BlockDoubleStoneBlockSlab4(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    DoubleStoneBlockSlab4 {

   override fun getVerticalHalf(): VerticalHalf {
       return VerticalHalf.valueOf(this.getStringState("minecraft:vertical_half"))
   }

   override fun setVerticalHalf(value: VerticalHalf): BlockDoubleStoneBlockSlab4 {
       return this.setState("minecraft:vertical_half", value.name.lowercase())
   }

   override fun getStoneSlabType4(): StoneSlabType4 {
       return StoneSlabType4.valueOf(this.getStringState("stone_slab_type_4"))
   }

   override fun setStoneSlabType4(value: StoneSlabType4): BlockDoubleStoneBlockSlab4 {
       return this.setState("stone_slab_type_4", value.name.lowercase())
   }

    override fun getDrops(item: Item): MutableList<Item> {
        return when (this.getType()) {
            BlockType.DOUBLE_STONE_BLOCK_SLAB4-> {
                this.createItemDrop(item, Item.create<ItemStoneBlockSlab4>(ItemType.STONE_BLOCK_SLAB4).apply {
                    this.setAmount(2)
                    this.setStoneSlabType4(this@BlockDoubleStoneBlockSlab4.getStoneSlabType4())
                })
            }
            else -> mutableListOf()
        }
    }

    override fun toItem(): Item {
        return Item.create<ItemStoneBlockSlab4>(ItemType.STONE_BLOCK_SLAB4).apply {
            this.setStoneSlabType4(this@BlockDoubleStoneBlockSlab4.getStoneSlabType4())
        }
    }
}