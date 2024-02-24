package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.SandType

interface Sand : Block {

   fun getSandType(): SandType

   fun setSandType(value: SandType): Sand

}