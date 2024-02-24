package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface DecoratedPot : Block {

   fun getDirection(): Direction

   fun setDirection(value: Direction): DecoratedPot

}