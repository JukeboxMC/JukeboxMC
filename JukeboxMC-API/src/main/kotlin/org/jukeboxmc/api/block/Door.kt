package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface Door : Block {

   fun isOpen(): Boolean

   fun setOpen(value: Boolean): Door

   fun isUpperBlock(): Boolean

   fun setUpperBlock(value: Boolean): Door

   fun isDoorHinge(): Boolean

   fun setDoorHinge(value: Boolean): Door

   fun getDirection(): Direction

   fun setDirection(value: Direction): Door

}