package org.jukeboxmc.world.generator.terra.delegate;

import com.dfsek.terra.api.util.MathUtil;
import com.dfsek.terra.api.world.biome.Biome;
import com.dfsek.terra.api.world.biome.generation.BiomeProvider;

import java.util.WeakHashMap;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BiomeProviderDelegate implements BiomeProvider {

    private final BiomeProvider delegate;
    private final WeakHashMap<Long, Biome> cacheMap;

    public BiomeProviderDelegate( BiomeProvider delegate ) {
        this.delegate = delegate;
        cacheMap = new WeakHashMap<>();
    }

    @Override
    public Biome getBiome( int x, int z, long seed ) {
        final var hash = MathUtil.squash( x, z );
        final var obj = cacheMap.get( hash );
        if ( obj != null ) {
            return obj;
        }
        final var tmp = delegate.getBiome( x, z, seed );
        cacheMap.put( hash, tmp );
        return tmp;
    }

    @Override
    public Iterable<Biome> getBiomes() {
        return null;
    }
}
