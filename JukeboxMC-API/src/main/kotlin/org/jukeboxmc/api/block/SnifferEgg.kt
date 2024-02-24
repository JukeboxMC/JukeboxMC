package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.CrackedState

interface SnifferEgg : Block {

   fun getCrackedState(): CrackedState

   fun setCrackedState(value: CrackedState): SnifferEgg

}