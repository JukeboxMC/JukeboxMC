package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface FenceGate : Block {

   fun isOpen(): Boolean

   fun setOpen(value: Boolean): FenceGate

   fun getDirection(): Direction

   fun setDirection(value: Direction): FenceGate

   fun isInWall(): Boolean

   fun setInWall(value: Boolean): FenceGate

}