package org.jukeboxmc.server.block.palette

interface RuntimeDataSerializer<V> {
    fun serialize(value: V): Int
}

