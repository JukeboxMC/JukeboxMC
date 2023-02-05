package org.jukeboxmc.world.generator.biome;

import org.jukeboxmc.world.Biome;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BiomeGrid {

    public final byte[] biomes = new byte[256];

    public Biome getBiome( int x, int z ) {
        return Biome.findById( this.biomes[x | z << 4] & 0xff );
    }
}
