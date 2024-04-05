package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.CherryWood
import org.jukeboxmc.api.block.data.Axis
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockCherryWood(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    CherryWood {

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

    override fun setPillarAxis(value: Axis): BlockCherryWood {
        return this.setState("pillar_axis", value.name.lowercase())
    }

    override fun isStripped(): Boolean {
        return this.getBooleanState("stripped_bit")
    }

    override fun setStripped(value: Boolean): BlockCherryWood {
        return this.setState("stripped_bit", value.toByte())
    }
}