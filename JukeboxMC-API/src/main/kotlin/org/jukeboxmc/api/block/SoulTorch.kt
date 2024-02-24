package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.TorchFacing

interface SoulTorch : Block {

   fun getTorchFacingDirection(): TorchFacing

   fun setTorchFacingDirection(value: TorchFacing): SoulTorch

}