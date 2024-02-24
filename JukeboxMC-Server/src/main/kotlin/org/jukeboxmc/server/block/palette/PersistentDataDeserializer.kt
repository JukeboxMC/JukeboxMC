package org.jukeboxmc.server.block.palette

import org.cloudburstmc.nbt.NbtMap

interface PersistentDataDeserializer<V> {
    fun deserialize(nbtMap: NbtMap): V
}

