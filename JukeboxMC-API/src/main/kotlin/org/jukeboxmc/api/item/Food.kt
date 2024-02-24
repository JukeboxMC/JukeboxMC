package org.jukeboxmc.api.item

interface Food {
    fun getSaturation(): Float

    fun getHunger(): Int
}