package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface Dispenser : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): Dispenser

   fun isTriggered(): Boolean

   fun setTriggered(value: Boolean): Dispenser

}