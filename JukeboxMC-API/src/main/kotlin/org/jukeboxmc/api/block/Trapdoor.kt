package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface Trapdoor : Block {

   fun isOpen(): Boolean

   fun setOpen(value: Boolean): Trapdoor

   fun getDirection(): Direction

   fun setDirection(value: Direction): Trapdoor

   fun isUpsideDown(): Boolean

   fun setUpsideDown(value: Boolean): Trapdoor

}