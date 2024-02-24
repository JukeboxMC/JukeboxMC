package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.SandStoneType

interface Sandstone : Block {

   fun getSandStoneType(): SandStoneType

   fun setSandStoneType(value: SandStoneType): Sandstone

}