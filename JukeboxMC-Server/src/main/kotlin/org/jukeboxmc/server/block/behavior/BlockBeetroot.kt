package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Beetroot
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.server.block.JukeboxBlock
import kotlin.math.min
import kotlin.random.Random

class BlockBeetroot(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Beetroot {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getGrowth(): Int {
        return this.getIntState("growth")
    }

    override fun setGrowth(value: Int): BlockBeetroot {
        return this.setState("growth", value)
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return if (this.getGrowth() != 7) {
            mutableListOf(Item.create(ItemType.BEETROOT_SEEDS, 1))
        } else {
            var amount = 2
            val attempts = 3 + min(
                0, if (item.hasEnchantment(EnchantmentType.FORTUNE)) item.getEnchantment(
                    EnchantmentType.FORTUNE
                )!!.getLevel() else 0
            )
            for (i in 0..<attempts) {
                if (Random.nextDouble() <= 0.5714286) {
                    amount++
                }
            }
            mutableListOf(Item.create(ItemType.BEETROOT_SEEDS, amount), Item.create(ItemType.BEETROOT, 1))
        }
    }
}