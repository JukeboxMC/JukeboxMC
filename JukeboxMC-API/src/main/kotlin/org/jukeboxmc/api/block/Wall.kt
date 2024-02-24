package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.WallConnectionType

interface Wall : Block {

   fun getWallConnectionTypeEast(): WallConnectionType

   fun setWallConnectionTypeEast(value: WallConnectionType): Wall

   fun isWallPost(): Boolean

   fun setWallPost(value: Boolean): Wall

   fun getWallConnectionTypeSouth(): WallConnectionType

   fun setWallConnectionTypeSouth(value: WallConnectionType): Wall

   fun getWallConnectionTypeWest(): WallConnectionType

   fun setWallConnectionTypeWest(value: WallConnectionType): Wall

   fun getWallConnectionTypeNorth(): WallConnectionType

   fun setWallConnectionTypeNorth(value: WallConnectionType): Wall

}