package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.TorchFacing

interface Torch : Block {

   fun getTorchFacingDirection(): TorchFacing

   fun setTorchFacingDirection(value: TorchFacing): Torch

}