package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BorderBlock
import org.jukeboxmc.api.block.data.WallConnectionType
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BorderBlock(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    BorderBlock {

    override fun getWallConnectionTypeEast(): WallConnectionType {
        return WallConnectionType.valueOf(this.getStringState("wall_connection_type_east"))
    }

    override fun setWallConnectionTypeEast(value: WallConnectionType): BorderBlock {
        return this.setState("wall_connection_type_east", value.name.lowercase())
    }

    override fun isWallPost(): Boolean {
        return this.getBooleanState("wall_post_bit")
    }

    override fun setWallPost(value: Boolean): BorderBlock {
        return this.setState("wall_post_bit", value.toByte())
    }

    override fun getWallConnectionTypeSouth(): WallConnectionType {
        return WallConnectionType.valueOf(this.getStringState("wall_connection_type_south"))
    }

    override fun setWallConnectionTypeSouth(value: WallConnectionType): BorderBlock {
        return this.setState("wall_connection_type_south", value.name.lowercase())
    }

    override fun getWallConnectionTypeWest(): WallConnectionType {
        return WallConnectionType.valueOf(this.getStringState("wall_connection_type_west"))
    }

    override fun setWallConnectionTypeWest(value: WallConnectionType): BorderBlock {
        return this.setState("wall_connection_type_west", value.name.lowercase())
    }

    override fun getWallConnectionTypeNorth(): WallConnectionType {
        return WallConnectionType.valueOf(this.getStringState("wall_connection_type_north"))
    }

    override fun setWallConnectionTypeNorth(value: WallConnectionType): BorderBlock {
        return this.setState("wall_connection_type_north", value.name.lowercase())
    }
}