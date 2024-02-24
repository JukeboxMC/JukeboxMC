package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.WallType

interface CobblestoneWall : Block, Wall {

   fun getWallBlockType(): WallType

   fun setWallBlockType(value: WallType): CobblestoneWall

}