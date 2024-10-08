package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.QuartzBlock
import org.jukeboxmc.api.block.data.Axis
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockQuartzBlock(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    QuartzBlock {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        this.setPillarAxis(blockFace.toAxis())
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun getPillarAxis(): Axis {
        return Axis.valueOf(this.getStringState("pillar_axis"))
    }

    override fun setPillarAxis(value: Axis): QuartzBlock {
        return this.setState("pillar_axis", value.name.lowercase())
    }
}