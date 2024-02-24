package org.jukeboxmc.api.block

interface SeaPickle : Block {

   fun isDead(): Boolean

   fun setDead(value: Boolean): SeaPickle

   fun getClusterCount(): Int

   fun setClusterCount(value: Int): SeaPickle

}