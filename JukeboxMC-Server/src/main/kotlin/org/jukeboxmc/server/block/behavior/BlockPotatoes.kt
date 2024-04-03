package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Potatoes
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.server.block.JukeboxBlock
import kotlin.math.min
import kotlin.random.Random

class BlockPotatoes(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Potatoes {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getGrowth(): Int {
       return this.getIntState("growth")
   }

   override fun setGrowth(value: Int): Potatoes {
       return this.setState("growth", value)
   }

    override fun getDrops(item: Item): MutableList<Item> {
        if (this.getGrowth() != 7) return mutableListOf()
        var amount = 2 + Random.nextInt(4)
        val attempts = 3 + min(0, if (item.hasEnchantment(EnchantmentType.FORTUNE)) item.getEnchantment(EnchantmentType.FORTUNE)!!.getLevel() else 0)
        for (i in 0..<attempts) {
            if (Random.nextDouble() <= 0.5714286) {
                amount++
            }
        }
        val potatoes = mutableListOf(Item.create(ItemType.POTATO, amount))
        if (Random.nextInt(100) <= 2) {
            potatoes.add(Item.create(ItemType.POISONOUS_POTATO, 1))
        }
        return potatoes
    }
}