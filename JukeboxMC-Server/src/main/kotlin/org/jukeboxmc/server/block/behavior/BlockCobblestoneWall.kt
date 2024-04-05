package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.CobblestoneWall
import org.jukeboxmc.api.block.data.WallType

class BlockCobblestoneWall(identifier: Identifier, blockStates: NbtMap?) : BlockWall(identifier, blockStates),
    CobblestoneWall {

    override fun getWallBlockType(): WallType {
        return WallType.valueOf(this.getStringState("wall_block_type"))
    }

    override fun setWallBlockType(value: WallType): BlockCobblestoneWall {
        return this.setState("wall_block_type", value.name.lowercase())
    }
}