package org.jukeboxmc.server.world.chunk.serializer

import org.jukeboxmc.api.world.Biome
import org.jukeboxmc.server.block.palette.RuntimeDataSerializer
import org.jukeboxmc.server.world.BiomeRegistry

class BiomeIdSerializer : RuntimeDataSerializer<Biome> {
    override fun serialize(value: Biome): Int {
        return BiomeRegistry.getBiomeId(value)
    }
}