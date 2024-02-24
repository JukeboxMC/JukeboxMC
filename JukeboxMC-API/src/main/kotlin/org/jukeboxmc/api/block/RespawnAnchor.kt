package org.jukeboxmc.api.block

interface RespawnAnchor : Block {

   fun getRespawnAnchorCharge(): Int

   fun setRespawnAnchorCharge(value: Int): RespawnAnchor

}