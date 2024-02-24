package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.FlowerType

interface RedFlower : Block {

   fun getFlowerType(): FlowerType

   fun setFlowerType(value: FlowerType): RedFlower

}