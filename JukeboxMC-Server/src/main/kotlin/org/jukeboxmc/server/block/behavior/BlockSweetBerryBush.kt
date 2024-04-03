package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SweetBerryBush
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.server.block.JukeboxBlock
import kotlin.random.Random

class BlockSweetBerryBush(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    SweetBerryBush {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getGrowth(): Int {
        return this.getIntState("growth")
    }

    override fun setGrowth(value: Int): SweetBerryBush {
        return this.setState("growth", value)
    }

    override fun getDrops(item: Item): MutableList<Item> {
        var level = 0
        if (item.hasEnchantment(EnchantmentType.FORTUNE)) {
            level = item.getEnchantment(EnchantmentType.FORTUNE)!!.getLevel()
        }
        val growth = this.getGrowth()
        return if (growth < 3) {
            mutableListOf()
        } else if (growth in 3..6) {
            mutableListOf(Item.create(ItemType.SWEET_BERRIES, 1 + Random.nextInt(2) + level))
        } else {
            mutableListOf(Item.create(ItemType.SWEET_BERRIES, 2 + Random.nextInt(2) + level))
        }
    }
}