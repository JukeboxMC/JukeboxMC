package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.AmethystCluster
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.ToolType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld
import kotlin.random.Random

class BlockAmethystCluster(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    AmethystCluster {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        if (!this.getRelative(BlockFace.DOWN).isSolid()) {
            return false
        }
        this.setBlockFace(blockFace)
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun getBlockFace(): BlockFace {
        return BlockFace.valueOf(this.getStringState("minecraft:block_face"))
    }

    override fun setBlockFace(value: BlockFace): BlockAmethystCluster {
        return this.setState("minecraft:block_face", value.name.lowercase())
    }

    override fun getDrops(item: Item): MutableList<Item> {
        var amount = 2
        if (item.getToolType() == ToolType.PICKAXE) {
            amount = 4
            if (item.hasEnchantment(EnchantmentType.FORTUNE) && item.getEnchantment(EnchantmentType.FORTUNE) != null) {
                amount = Random.nextInt(amount, amount + item.getEnchantment(EnchantmentType.FORTUNE)!!.getLevel() * amount) + 1
            }
        }
        return mutableListOf(Item.create(ItemType.AMETHYST_SHARD, amount))
    }

}