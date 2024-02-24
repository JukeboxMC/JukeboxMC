package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.WallConnectionType

interface BorderBlock : Block {

   fun getWallConnectionTypeEast(): WallConnectionType

   fun setWallConnectionTypeEast(value: WallConnectionType): BorderBlock

   fun isWallPost(): Boolean

   fun setWallPost(value: Boolean): BorderBlock

   fun getWallConnectionTypeSouth(): WallConnectionType

   fun setWallConnectionTypeSouth(value: WallConnectionType): BorderBlock

   fun getWallConnectionTypeWest(): WallConnectionType

   fun setWallConnectionTypeWest(value: WallConnectionType): BorderBlock

   fun getWallConnectionTypeNorth(): WallConnectionType

   fun setWallConnectionTypeNorth(value: WallConnectionType): BorderBlock

}