package org.jukeboxmc.world.generator.terra.delegate;

import com.dfsek.terra.api.block.entity.BlockEntity;
import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.entity.Entity;
import com.dfsek.terra.api.entity.EntityType;
import com.dfsek.terra.api.world.ServerWorld;
import com.dfsek.terra.api.world.biome.generation.BiomeProvider;
import com.dfsek.terra.api.world.chunk.Chunk;
import com.dfsek.terra.api.world.chunk.generation.ChunkGenerator;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.generator.TerraGenerator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public record TerraServerWorld(TerraGenerator terraGenerator, ChunkGenerator chunkGenerator, ConfigPack configPack,
                               BiomeProvider biomeProvider) implements ServerWorld {

    @Override
    public void setBlockState( int x, int y, int z, BlockState blockState, boolean b ) {
        this.terraGenerator.getChunk( x >> 4, z >> 4 ).setBlock( new Vector( x, y, z ), 0, ( (BlockStateDelegate) blockState ).getHandle().getRuntimeId() );
    }

    @Override
    public Entity spawnEntity( double x, double y, double z, EntityType entityType ) {
        return null;
    }

    @Override
    public BlockState getBlockState( int x, int y, int z ) {
        return TerraAdapter.adapt( this.terraGenerator.getChunk( x >> 4, z >> 4 ).getBlock( x, y, z, 0 ) );
    }

    @Override
    public BlockEntity getBlockEntity( int x, int y, int z ) {
        return null;
    }

    @Override
    public ChunkGenerator getGenerator() {
        return chunkGenerator;
    }

    @Override
    public BiomeProvider getBiomeProvider() {
        return biomeProvider;
    }

    @Override
    public ConfigPack getPack() {
        return configPack;
    }

    @Override
    public long getSeed() {
        return this.terraGenerator.getWorld().getSeed();
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
    public TerraGenerator getHandle() {
        return this.terraGenerator;
    }

    @Override
    public Chunk getChunkAt( int i, int i1 ) {
        return new TerraChunkDelegate( this.terraGenerator, this.terraGenerator.getChunk( i, i1 ), this.chunkGenerator, this.configPack, this.biomeProvider );
    }
}
