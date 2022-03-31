package org.jukeboxmc.world.generator;

import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.world.biome.generation.BiomeProvider;
import com.dfsek.terra.api.world.chunk.generation.ChunkGenerator;
import com.dfsek.terra.api.world.chunk.generation.util.GeneratorWrapper;
import com.dfsek.terra.api.world.info.WorldProperties;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.generator.terra.TerraPlatform;
import org.jukeboxmc.world.generator.terra.delegate.BiomeProviderDelegate;
import org.jukeboxmc.world.generator.terra.delegate.TerraProtoChunk;
import org.jukeboxmc.world.generator.terra.delegate.TerraProtoWorld;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TerraGenerator extends WorldGenerator implements GeneratorWrapper {

    private WeakReference<ChunkGenerator> delegate;
    private ConfigPack pack;

    private WeakReference<BiomeProviderDelegate> biomesProvider = new WeakReference<>( null );

    private final WorldProperties worldProperties;

    public TerraGenerator() {
        String packName = "default";

        try {
            this.delegate = new WeakReference<>( createGenerator( packName ) );
            this.pack = createConfigPack( packName );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        this.worldProperties = new WorldProperties() {
            @Override
            public long getSeed() {
                return TerraGenerator.this.getWorld().getSeed();
            }

            @Override
            public int getMaxHeight() {
                return 319;
            }

            @Override
            public int getMinHeight() {
                return -64;
            }

            @Override
            public Object getHandle() {
                return null;
            }
        };
    }

    @Override
    public void generate( int chunkX, int chunkZ ) {
        Chunk chunk = this.getChunk( chunkX, chunkZ );

        getChunkGeneratorDelegate().generateChunkData( new TerraProtoChunk( chunk ), this.worldProperties,
                getBiomesProviderDelegate(), chunkX, chunkZ );

        for ( int x = 0; x < 16; x++ ) {
            for ( int z = 0; z < 16; z++ ) {
                Biome biome = (Biome) getBiomesProviderDelegate().getBiome( (chunkX << 4) + x, (chunkZ << 4) + z, this.getWorld().getSeed() ).getPlatformBiome().getHandle();

                for ( int y = chunk.getMinY(); y <= chunk.getMaxY(); y++ ) {
                    chunk.setBiome( x, y, z, biome );
                }
            }
        }
    }

    @Override
    public void populate( int chunkX, int chunkZ ) {
        var tmp = new TerraProtoWorld( this, getChunkGeneratorDelegate(), this.pack, getBiomesProviderDelegate(), chunkX, chunkZ );
        for ( var generationStage : this.pack.getStages() ) {
            try {
                generationStage.populate( tmp );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        this.delegate.clear();
    }

    @Override
    public Vector getSpawnLocation() {
        return new Vector( 0.5f, 120, 0.5f );
    }

    @Override
    public ChunkGenerator getHandle() {
        return getChunkGeneratorDelegate();
    }

    private ChunkGenerator getChunkGeneratorDelegate() {
        final var gen = this.delegate.get();
        if ( gen != null ) {
            return gen;
        }
        final var newGen = createGenerator( this.pack );
        this.delegate = new WeakReference<>( newGen );
        return newGen;
    }

    private BiomeProvider getBiomesProviderDelegate() {
        final var provider = biomesProvider.get();
        if ( provider != null ) {
            return provider;
        }
        final var newProvider = this.pack.getBiomeProvider();
        this.biomesProvider = new WeakReference<>( new BiomeProviderDelegate( newProvider ) );
        return newProvider;
    }

    private static ChunkGenerator createGenerator( ConfigPack config ) {
        return config.getGeneratorProvider().newInstance( config );
    }

    private static ChunkGenerator createGenerator( final String packName ) {
        var config = createConfigPack( packName );
        return config.getGeneratorProvider().newInstance( config );
    }

    private static ConfigPack createConfigPack( final String packName ) {
        return TerraPlatform.getInstance().getConfigRegistry().getByID( packName ).orElseGet(
                () -> TerraPlatform.getInstance().getConfigRegistry().getByID( "TerraGenerator:" + packName ).orElseThrow()
        );
    }
}

