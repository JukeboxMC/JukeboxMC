package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.LeverDirection

interface Lever : Block {

   fun isOpen(): Boolean

   fun setOpen(value: Boolean): Lever

   fun getLeverDirection(): LeverDirection

   fun setLeverDirection(value: LeverDirection): Lever

}