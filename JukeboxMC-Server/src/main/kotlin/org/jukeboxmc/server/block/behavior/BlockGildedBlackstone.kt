package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.ToolType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockGildedBlackstone(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    private val random = java.util.Random()

    override fun getDrops(item: Item): MutableList<Item> {
        if (item.getToolType() != ToolType.PICKAXE) {
            return mutableListOf()
        }
        var level = 0
        if (item.hasEnchantment(EnchantmentType.FORTUNE)) {
            level = item.getEnchantment(EnchantmentType.FORTUNE)!!.getLevel()
        }
        val probability = when (level) {
            0 -> {
                10.0f
            }

            1 -> {
                14.29f
            }

            2 -> {
                25.0f
            }

            else -> {
                100.0f
            }
        }
        return mutableListOf(
            if (this.random.nextFloat(100.0f) <= probability)
                Item.create(ItemType.GOLD_NUGGET, this.random.nextInt(1, 5) + 1)
            else
                this.toItem()
        )
    }

}