package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.PointedDripstone
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.DripstoneThickness
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockPointedDripstone(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    PointedDripstone {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val block = world.getBlock(placePosition)
        if (block is BlockWater && block.getLiquidDepth() == 0) {
            world.setBlock(placePosition, block, 1, false)
        }
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun isHanging(): Boolean {
        return this.getBooleanState("hanging")
    }

    override fun setHanging(value: Boolean): PointedDripstone {
        return this.setState("hanging", value.toByte())
    }

    override fun getDripstoneThickness(): DripstoneThickness {
        return DripstoneThickness.valueOf(this.getStringState("dripstone_thickness"))
    }

    override fun setDripstoneThickness(value: DripstoneThickness): PointedDripstone {
        return this.setState("dripstone_thickness", value.name.lowercase())
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}