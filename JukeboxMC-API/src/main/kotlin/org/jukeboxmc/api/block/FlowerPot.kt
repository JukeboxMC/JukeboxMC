package org.jukeboxmc.api.block

interface FlowerPot : Block {

   fun isUpdate(): Boolean

   fun setUpdate(value: Boolean): FlowerPot

}