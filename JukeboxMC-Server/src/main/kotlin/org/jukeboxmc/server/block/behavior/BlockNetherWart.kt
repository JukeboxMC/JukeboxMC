package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.NetherWart
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.server.block.JukeboxBlock
import kotlin.random.Random

class BlockNetherWart(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), NetherWart {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getAge(): Int {
       return this.getIntState("age")
   }

   override fun setAge(value: Int): NetherWart {
       return this.setState("age", value)
   }

    override fun getDrops(item: Item): MutableList<Item> {
        if (this.getAge() < 3) {
            return mutableListOf()
        }
        var level = 0
        if (item.hasEnchantment(EnchantmentType.FORTUNE)) {
            level = item.getEnchantment(EnchantmentType.FORTUNE)!!.getLevel()
        }
        return mutableListOf(Item.create(ItemType.NETHER_WART, 2 + Random.nextInt(3) + level))
    }
}