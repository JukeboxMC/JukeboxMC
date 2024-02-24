package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.AnvilDamage
import org.jukeboxmc.api.block.data.Direction

interface Anvil : Block {

   fun getCardinalDirection(): Direction

   fun setCardinalDirection(value: Direction): Anvil

   fun getDamage(): AnvilDamage

   fun setDamage(value: AnvilDamage): Anvil

}