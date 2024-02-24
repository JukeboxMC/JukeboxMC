package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.SandStoneType

interface RedSandstone : Block {

   fun getSandStoneType(): SandStoneType

   fun setSandStoneType(value: SandStoneType): RedSandstone

}