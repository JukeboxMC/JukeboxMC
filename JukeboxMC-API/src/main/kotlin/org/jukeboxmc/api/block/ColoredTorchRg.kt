package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.TorchFacing

interface ColoredTorchRg : Block {

   fun getTorchFacingDirection(): TorchFacing

   fun setTorchFacingDirection(value: TorchFacing): ColoredTorchRg

   fun isColor(): Boolean

   fun setColor(value: Boolean): ColoredTorchRg

}