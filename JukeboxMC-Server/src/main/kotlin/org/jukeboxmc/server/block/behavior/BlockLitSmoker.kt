package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.LitSmoker
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.blockentity.JukeboxBlockEntityFurnace
import org.jukeboxmc.server.blockentity.JukeboxBlockEntitySmoker
import org.jukeboxmc.server.extensions.toJukeboxBlockEntity
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockLitSmoker(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), LitSmoker {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        this.setCardinalDirection(player.getDirection().opposite())
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun interact(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        world.getBlockEntity(this.getLocation())?.toJukeboxBlockEntity()
            ?.interact(player, blockPosition, clickedPosition, blockFace, itemInHand)
        return true
    }

    override fun onBlockBreak(breakLocation: Vector) {
        this.getWorld().getBlockEntity(breakLocation)?.let {
            it as JukeboxBlockEntitySmoker
            val inventory = it.getSmokerInventory()
            for (item in inventory.getContents()) {
                if (item.getType() == ItemType.AIR) continue
                this.getWorld().dropItemNaturally(breakLocation, item)
            }
            inventory.clear()
        }
        super.onBlockBreak(breakLocation)
    }

    override fun getCardinalDirection(): Direction {
        return Direction.valueOf(this.getStringState("minecraft:cardinal_direction"))
    }

    override fun setCardinalDirection(value: Direction): LitSmoker {
        return this.setState("minecraft:cardinal_direction", value.name.lowercase())
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return this.createItemDrop(item, Item.create(ItemType.SMOKER))
    }
}