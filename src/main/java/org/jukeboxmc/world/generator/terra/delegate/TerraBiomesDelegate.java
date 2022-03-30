package org.jukeboxmc.world.generator.terra.delegate;

import com.dfsek.terra.api.world.biome.PlatformBiome;
import org.jukeboxmc.world.Biome;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public record TerraBiomesDelegate(Biome biome) implements PlatformBiome {

    @Override
    public Biome getHandle() {
        return biome;
    }
}
