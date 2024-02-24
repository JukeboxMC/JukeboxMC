package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface Beehive : Block {

   fun getDirection(): Direction

   fun setDirection(value: Direction): Beehive

   fun getHoneyLevel(): Int

   fun setHoneyLevel(value: Int): Beehive

}