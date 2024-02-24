package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SmallAmethystBud
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockSmallAmethystBud(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), SmallAmethystBud {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val blockDown = this.getRelative(BlockFace.DOWN)
        if (!blockDown.isSolid()) return false
        this.setBlockFace(blockFace)
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

   override fun getBlockFace(): BlockFace {
       return BlockFace.valueOf(this.getStringState("minecraft:block_face"))
   }

   override fun setBlockFace(value: BlockFace): SmallAmethystBud {
       return this.setState("minecraft:block_face", value.name.lowercase())
   }
}