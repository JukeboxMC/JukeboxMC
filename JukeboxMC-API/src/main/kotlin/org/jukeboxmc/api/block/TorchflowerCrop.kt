package org.jukeboxmc.api.block

interface TorchflowerCrop : Block {

   fun getGrowth(): Int

   fun setGrowth(value: Int): TorchflowerCrop

}