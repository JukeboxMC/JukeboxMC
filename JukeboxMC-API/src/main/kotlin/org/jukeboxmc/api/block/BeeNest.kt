package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface BeeNest : Block {

   fun getDirection(): Direction

   fun setDirection(value: Direction): BeeNest

   fun getHoneyLevel(): Int

   fun setHoneyLevel(value: Int): BeeNest

}