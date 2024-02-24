package org.jukeboxmc.api.block

interface LightBlock : Block {

   fun getBlockLightLevel(): Int

   fun setBlockLightLevel(value: Int): LightBlock

}