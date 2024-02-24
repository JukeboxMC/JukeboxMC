package org.jukeboxmc.api.block

interface Scaffolding : Block {

   fun getStability(): Int

   fun setStability(value: Int): Scaffolding

   fun isStabilityCheck(): Boolean

   fun setStabilityCheck(value: Boolean): Scaffolding

}