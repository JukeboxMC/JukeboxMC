package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.SpongeType

interface Sponge : Block {

   fun getSpongeType(): SpongeType

   fun setSpongeType(value: SpongeType): Sponge

}