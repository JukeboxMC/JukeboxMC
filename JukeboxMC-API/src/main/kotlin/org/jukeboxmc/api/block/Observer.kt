package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface Observer : Block {

   fun isPowered(): Boolean

   fun setPowered(value: Boolean): Observer

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): Observer

}