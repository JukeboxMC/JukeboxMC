package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.CandleCake
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockCandleCake(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    CandleCake {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        if(this.getRelative(BlockFace.DOWN).getType() == BlockType.AIR) return false
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
        if (this.isLit() && itemInHand.getType() != ItemType.FLINT_AND_STEEL) {
            this.setLit(false)
            world.playLevelSound(blockPosition, SoundEvent.FIZZ)
            return true
        } else if (!this.isLit() && itemInHand.getType() == ItemType.FLINT_AND_STEEL) {
            this.setLit(true)
            world.playLevelSound(blockPosition, SoundEvent.IGNITE)
            return true
        } else if (player.isHungry() || player.getGameMode() == GameMode.CREATIVE) {
            //TODO Handle cake eating
        }
        return false
    }

   override fun isLit(): Boolean {
       return this.getBooleanState("lit")
   }

   override fun setLit(value: Boolean): BlockCandleCake {
       return this.setState("lit", value.toByte())
   }
}