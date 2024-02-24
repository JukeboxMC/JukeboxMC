package org.jukeboxmc.server.block.palette

import org.cloudburstmc.nbt.NbtMap

interface PersistentDataSerializer<V> {
    fun serialize(value: V): NbtMap
}

