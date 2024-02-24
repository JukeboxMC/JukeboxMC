package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.DirtType

interface Dirt : Block {

   fun getDirtType(): DirtType

   fun setDirtType(value: DirtType): Dirt

}