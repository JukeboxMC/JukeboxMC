package org.jukeboxmc.world.generator.noise.bukkit;

import java.util.Random;

/**
 * Creates perlin noise through unbiased octaves
 */
public class PerlinOctaveGenerator extends OctaveGenerator {

    public PerlinOctaveGenerator( long seed, int octaves ) {
        this( new Random( seed ), octaves );
    }

    public PerlinOctaveGenerator( Random rand, int octaves ) {
        super( createOctaves( rand, octaves ) );
    }

    private static NoiseGenerator[] createOctaves( Random rand, int octaves ) {
        NoiseGenerator[] result = new NoiseGenerator[octaves];
        for ( int i = 0; i < octaves; ++i ) {
            result[i] = new PerlinNoiseGenerator( rand );
        }
        return result;
    }
}
