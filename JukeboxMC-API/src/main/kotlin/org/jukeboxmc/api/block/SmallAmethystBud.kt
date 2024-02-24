package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface SmallAmethystBud : Block {

   fun getBlockFace(): BlockFace

   fun setBlockFace(value: BlockFace): SmallAmethystBud

}