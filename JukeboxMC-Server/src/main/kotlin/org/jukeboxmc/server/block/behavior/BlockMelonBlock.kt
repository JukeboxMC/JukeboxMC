package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.server.block.JukeboxBlock
import kotlin.math.min
import kotlin.random.Random

class BlockMelonBlock(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun getDrops(item: Item): MutableList<Item> {
        var amount = 3 + Random.nextInt(5)
        if (item.hasEnchantment(EnchantmentType.FORTUNE) && item.getEnchantment(EnchantmentType.FORTUNE)!!
                .getLevel() >= 1
        ) {
            amount += Random.nextInt(item.getEnchantment(EnchantmentType.FORTUNE)!!.getLevel() + 1)
        }
        return mutableListOf(Item.create(ItemType.MELON_SLICE, min(9, amount)))
    }

}