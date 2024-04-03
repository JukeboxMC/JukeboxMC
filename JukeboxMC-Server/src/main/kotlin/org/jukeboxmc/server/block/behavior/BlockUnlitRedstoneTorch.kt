package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.UnlitRedstoneTorch
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.TorchFacing
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockUnlitRedstoneTorch(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    UnlitRedstoneTorch {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        if (!world.getBlock(blockPosition).isTransparent() && blockFace !== BlockFace.DOWN) {
            this.setTorchFacingDirection(blockFace.opposite().torchFacing())
            return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
        }
        if (!world.getBlock(placePosition.subtract(0F, 1F, 0F)).isTransparent()) {
            this.setTorchFacingDirection(TorchFacing.TOP)
            return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
        }
        return false
    }

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getTorchFacingDirection(): TorchFacing {
        return TorchFacing.valueOf(this.getStringState("torch_facing_direction"))
    }

    override fun setTorchFacingDirection(value: TorchFacing): UnlitRedstoneTorch {
        return this.setState("torch_facing_direction", value.name.lowercase())
    }

    override fun toItem(): Item {
        return Item.create(ItemType.REDSTONE_TORCH)
    }
}