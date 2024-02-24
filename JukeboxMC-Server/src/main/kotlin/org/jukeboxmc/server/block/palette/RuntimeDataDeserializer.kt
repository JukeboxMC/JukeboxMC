package org.jukeboxmc.server.block.palette

interface RuntimeDataDeserializer<V> {
    fun deserialize(id: Int): V
}

