package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface CoralWallFan : Block {

   fun getCoralDirection(): Direction

   fun setCoralDirection(value: Direction): CoralWallFan

}