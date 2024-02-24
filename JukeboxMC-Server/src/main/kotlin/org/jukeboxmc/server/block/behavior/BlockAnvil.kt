package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Anvil
import org.jukeboxmc.api.block.data.AnvilDamage
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockAnvil(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Anvil {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        this.setCardinalDirection(player.getDirection().getRightDirection().opposite())
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun interact(player: JukeboxPlayer, world: JukeboxWorld, blockPosition: Vector, clickedPosition: Vector, blockFace: BlockFace, itemInHand: JukeboxItem): Boolean {
        player.openInventory(player.getAnvilInventory(), blockPosition)
        return true
    }

   override fun getCardinalDirection(): Direction {
       return Direction.valueOf(this.getStringState("minecraft:cardinal_direction"))
   }

   override fun setCardinalDirection(value: Direction): BlockAnvil {
       return this.setState("minecraft:cardinal_direction", value.name.lowercase())
   }

   override fun getDamage(): AnvilDamage {
       return AnvilDamage.valueOf(this.getStringState("damage"))
   }

   override fun setDamage(value: AnvilDamage): BlockAnvil {
       return this.setState("damage", value.name.lowercase())
   }
}