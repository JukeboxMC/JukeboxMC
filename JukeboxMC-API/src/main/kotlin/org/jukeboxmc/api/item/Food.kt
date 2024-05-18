package org.jukeboxmc.api.item

interface Food : Item {

    fun getSaturation(): Float

    fun getHunger(): Int
}