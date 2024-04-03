package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxItem
import kotlin.random.Random

class BlockCoalOre(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun getDrops(item: Item): MutableList<Item> {
        if (this.canBreakWithHand(item.toJukeboxItem())) {
            if (item.hasEnchantment(EnchantmentType.FORTUNE)) {
                val enchantment = item.getEnchantment(EnchantmentType.FORTUNE)!!
                val amount = when(enchantment.getLevel()) {
                    1 -> Random.nextInt(3)
                    2 -> Random.nextInt(4)
                    3 -> Random.nextInt(5)
                    else -> 1
                }
                return mutableListOf(Item.create(ItemType.COAL, amount))
            }
        }
        return mutableListOf()
    }

}