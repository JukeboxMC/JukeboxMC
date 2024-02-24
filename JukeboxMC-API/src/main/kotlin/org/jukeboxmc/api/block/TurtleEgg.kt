package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.CrackedState
import org.jukeboxmc.api.block.data.TurtleEggCount

interface TurtleEgg : Block {

   fun getCrackedState(): CrackedState

   fun setCrackedState(value: CrackedState): TurtleEgg

   fun getTurtleEggCount(): TurtleEggCount

   fun setTurtleEggCount(value: TurtleEggCount): TurtleEgg

}