package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.PistonArmCollision
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.server.block.JukeboxBlock

class BlockPistonArmCollision(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    PistonArmCollision {

    override fun getFacingDirection(): BlockFace {
        return BlockFace.entries[this.getIntState("facing_direction")]
    }

    override fun setFacingDirection(value: BlockFace): PistonArmCollision {
        return this.setState("facing_direction", value.ordinal)
    }
}