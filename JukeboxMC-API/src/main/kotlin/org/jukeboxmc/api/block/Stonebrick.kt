package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.StoneBrickType

interface Stonebrick : Block {

   fun getStoneBrickType(): StoneBrickType

   fun setStoneBrickType(value: StoneBrickType): Stonebrick

}