package org.jukeboxmc.world.generator.terra.delegate;

import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.world.ServerWorld;
import com.dfsek.terra.api.world.biome.generation.BiomeProvider;
import com.dfsek.terra.api.world.chunk.generation.ChunkGenerator;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.generator.TerraGenerator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public record TerraChunkDelegate(TerraGenerator terraGenerator, Chunk chunk, ChunkGenerator chunkGenerator, ConfigPack configPack,
                                 BiomeProvider biomeProvider) implements com.dfsek.terra.api.world.chunk.Chunk {
    @Override
    public void setBlock( int x, int y, int z, BlockState blockState, boolean b ) {
        this.setBlock( x, y, z, blockState );
    }

    @Override
    public void setBlock( int x, int y, int z, BlockState blockState ) {
        chunk.setBlock( x, y, z, 0, ( (BlockStateDelegate) blockState ).getHandle() );
    }

    @Override
    public BlockState getBlock( int x, int y, int z ) {
        return TerraAdapter.adapt( chunk.getBlock( x, y, z, 0 ) );
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public ServerWorld getWorld() {
        return new TerraServerWorld( this.terraGenerator, this.chunkGenerator, this.configPack, this.biomeProvider );
    }

    @Override
    public Chunk getHandle() {
        return this.chunk;
    }
}
