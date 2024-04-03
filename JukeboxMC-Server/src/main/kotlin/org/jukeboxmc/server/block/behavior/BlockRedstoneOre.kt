package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxItem
import kotlin.random.Random

class BlockRedstoneOre(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun getDrops(item: Item): MutableList<Item> {
        if (this.canBreakWithHand(item.toJukeboxItem())) {
            var level = 0
            if (item.hasEnchantment(EnchantmentType.FORTUNE) && item.getEnchantment(EnchantmentType.FORTUNE) != null) {
                level = item.getEnchantment(EnchantmentType.FORTUNE)!!.getLevel()
            }
            return mutableListOf(Item.create(ItemType.REDSTONE, Random.nextInt(3, 5 + level) + 1))
        }
        return mutableListOf()
    }

}