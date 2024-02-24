package org.jukeboxmc.api.block

interface RedMushroomBlock : Block {

   fun getHugeMushrooms(): Int

   fun setHugeMushrooms(value: Int): RedMushroomBlock

}