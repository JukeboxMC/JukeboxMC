package org.jukeboxmc.world.generator.noise.bukkit;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Creates perlin noise through unbiased octaves
 */
public class PerlinOctaveGenerator extends OctaveGenerator {

    public PerlinOctaveGenerator( long seed, int octaves ) {
        this( new Random( seed ), octaves );
    }

    public PerlinOctaveGenerator(@NotNull Random rand, int octaves ) {
        super( createOctaves( rand, octaves ) );
    }

    private static NoiseGenerator @NotNull [] createOctaves(@NotNull Random rand, int octaves ) {
        NoiseGenerator[] result = new NoiseGenerator[octaves];
        for ( int i = 0; i < octaves; ++i ) {
            result[i] = new PerlinNoiseGenerator( rand );
        }
        return result;
    }
}
