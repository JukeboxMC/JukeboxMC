package org.jukeboxmc.server.world.chunk.serializer

import org.jukeboxmc.api.world.Biome
import org.jukeboxmc.server.block.palette.RuntimeDataDeserializer
import org.jukeboxmc.server.world.BiomeRegistry

class BiomeIdDeserializer : RuntimeDataDeserializer<Biome> {

    override fun deserialize(id: Int): Biome {
        return BiomeRegistry.getBiome(id)
    }
}