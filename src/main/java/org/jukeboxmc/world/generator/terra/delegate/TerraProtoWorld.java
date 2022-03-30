package org.jukeboxmc.world.generator.terra.delegate;

import com.dfsek.terra.api.block.entity.BlockEntity;
import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.entity.Entity;
import com.dfsek.terra.api.entity.EntityType;
import com.dfsek.terra.api.util.vector.Vector3;
import com.dfsek.terra.api.world.ServerWorld;
import com.dfsek.terra.api.world.biome.generation.BiomeProvider;
import com.dfsek.terra.api.world.chunk.generation.ChunkGenerator;
import com.dfsek.terra.api.world.chunk.generation.ProtoWorld;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockSeagrass;
import org.jukeboxmc.block.BlockWater;
import org.jukeboxmc.block.BlockWaterlily;
import org.jukeboxmc.world.generator.TerraGenerator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public record TerraProtoWorld(TerraGenerator terraGenerator, ChunkGenerator chunkGenerator, ConfigPack configPack,
                              BiomeProvider biomeProvider, int centerChunkX, int centerChunkZ) implements ProtoWorld {

    @Override
    public int centerChunkX() {
        return centerChunkX;
    }

    @Override
    public int centerChunkZ() {
        return centerChunkZ;
    }

    @Override
    public ServerWorld getWorld() {
        return new TerraServerWorld( terraGenerator, chunkGenerator, configPack, biomeProvider );
    }

    @Override
    public void setBlockState( int x, int y, int z, BlockState blockState, boolean b ) {
        if ( blockState instanceof BlockStateDelegate pnxBlockState ) {
            Block block = this.terraGenerator.getChunk( x >> 4, z >> 4 ).getBlock( x, y, z, 0 );

            Block placeBlock = pnxBlockState.getHandle();
            if ( block instanceof BlockWaterlily || block instanceof BlockWater ) {
                if ( placeBlock instanceof BlockSeagrass ) {
                    Block downBlock = this.terraGenerator.getChunk( x >> 4, z >> 4 ).getBlock( x, y - 1, z, 0 );
                    if ( !( downBlock instanceof BlockWater ) ) {
                        this.terraGenerator.getChunk( x >> 4, z >> 4 ).setBlock( x, y, z, 0, placeBlock );
                        this.terraGenerator.getChunk( x >> 4, z >> 4 ).setBlock( x, y, z, 1, new BlockWater() );
                    }
                } else {
                    this.terraGenerator.getChunk( x >> 4, z >> 4 ).setBlock( x, y, z, 1, placeBlock );
                }
            }
            if ( !( placeBlock instanceof BlockSeagrass ) ) {
                this.terraGenerator.getChunk( x >> 4, z >> 4 ).setBlock( x, y, z, 0, placeBlock );
            }
        }
    }

    @Override
    public Entity spawnEntity( double x, double y, double z, EntityType entityType ) {
        return new Entity() {
            private Vector3 vector3 = Vector3.of( x, y, z );
            private ServerWorld world;

            @Override
            public Vector3 position() {
                return vector3;
            }

            @Override
            public void position( Vector3 vector3 ) {
                this.vector3 = vector3;
            }

            @Override
            public void world( ServerWorld serverWorld ) {
                this.world = serverWorld;
            }

            @Override
            public ServerWorld world() {
                return world;
            }

            @Override
            public Object getHandle() {
                return null;
            }
        };
    }

    @Override
    public BlockState getBlockState( int x, int y, int z ) {
        return TerraAdapter.adapt( terraGenerator.getChunk( x >> 4, z >> 4 ).getBlock( x, y, z, 0 ) );
    }

    @Override
    public BlockEntity getBlockEntity( int x, int y, int z ) {
        return null;
    }

    @Override
    public ChunkGenerator getGenerator() {
        return this.chunkGenerator;
    }

    @Override
    public BiomeProvider getBiomeProvider() {
        return this.biomeProvider;
    }

    @Override
    public ConfigPack getPack() {
        return this.configPack;
    }

    @Override
    public long getSeed() {
        return this.getWorld().getSeed();
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

}
