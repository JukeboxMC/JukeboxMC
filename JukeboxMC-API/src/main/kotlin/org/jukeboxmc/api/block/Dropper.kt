package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface Dropper : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): Dropper

   fun isTriggered(): Boolean

   fun setTriggered(value: Boolean): Dropper

}