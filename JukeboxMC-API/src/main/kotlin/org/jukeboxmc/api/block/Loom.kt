package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface Loom : Block {

   fun getDirection(): Direction

   fun setDirection(value: Direction): Loom

}