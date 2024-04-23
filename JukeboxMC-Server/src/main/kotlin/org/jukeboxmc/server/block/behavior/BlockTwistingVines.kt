package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.TwistingVines
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ToolType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockTwistingVines(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    TwistingVines {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getTwistingVinesAge(): Int {
        return this.getIntState("twisting_vines_age")
    }

    override fun setTwistingVinesAge(value: Int): TwistingVines {
        return this.setState("twisting_vines_age", value)
    }

    override fun getDrops(item: Item): MutableList<Item> {
        if (item.getToolType() == ToolType.SHEARS) {
            return mutableListOf(this.toItem())
        } else {
            var level = 0
            if (item.hasEnchantment(EnchantmentType.FORTUNE)) {
                level = item.getEnchantment(EnchantmentType.FORTUNE)!!.getLevel()
            }
            val probability = when (level) {
                0 -> {
                    33.0f
                }

                1 -> {
                    55.0f
                }

                2 -> {
                    77.0f
                }

                3 -> {
                    100.0f
                }

                else -> 0.0f
            }
            if (this.getRandom().nextFloat(100.0f) <= probability) {
                return mutableListOf(this.toItem())
            }
            return mutableListOf()
        }
    }
}