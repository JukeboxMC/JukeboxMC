package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.TorchFacing

interface ColoredTorchBp : Block {

   fun getTorchFacingDirection(): TorchFacing

   fun setTorchFacingDirection(value: TorchFacing): ColoredTorchBp

   fun isColor(): Boolean

   fun setColor(value: Boolean): ColoredTorchBp

}