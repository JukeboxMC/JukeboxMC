package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.Campfire
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockCampfire(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Campfire {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        if (this.getRelative(BlockFace.DOWN).getType() == BlockType.CAMPFIRE) return false
        this.setCardinalDirection(player.getDirection().opposite())
        BlockEntity.create(BlockEntityType.CAMPFIRE, this)?.spawn()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

   override fun getCardinalDirection(): Direction {
       return Direction.entries[this.getIntState("minecraft:cardinal_direction")]
   }

   override fun setCardinalDirection(value: Direction): BlockCampfire {
       return this.setState("minecraft:cardinal_direction", value.name.lowercase())
   }

   override fun isExtinguished(): Boolean {
       return this.getBooleanState("extinguished")
   }

   override fun setExtinguished(value: Boolean): BlockCampfire {
       return this.setState("extinguished", value.toByte())
   }

    override fun getDrops(item: Item): MutableList<Item> {
        return mutableListOf(Item.create(ItemType.CHARCOAL, 4))
    }
}