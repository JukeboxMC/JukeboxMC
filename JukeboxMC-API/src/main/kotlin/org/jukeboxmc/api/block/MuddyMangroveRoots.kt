package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Axis

interface MuddyMangroveRoots : Block {

   fun getPillarAxis(): Axis

   fun setPillarAxis(value: Axis): MuddyMangroveRoots

}