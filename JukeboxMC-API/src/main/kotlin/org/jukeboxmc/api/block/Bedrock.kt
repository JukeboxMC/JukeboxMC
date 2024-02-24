package org.jukeboxmc.api.block

interface Bedrock : Block {

   fun isInfiniburn(): Boolean

   fun setInfiniburn(value: Boolean): Bedrock

}