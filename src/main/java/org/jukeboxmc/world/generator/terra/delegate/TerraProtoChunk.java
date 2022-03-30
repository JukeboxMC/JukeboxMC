package org.jukeboxmc.world.generator.terra.delegate;

import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.world.chunk.generation.ProtoChunk;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockFlowingWater;
import org.jukeboxmc.block.BlockWater;
import org.jukeboxmc.block.BlockWaterlily;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public record TerraProtoChunk(Chunk chunk) implements ProtoChunk {

    @Override
    public int getMaxHeight() {
        return 384;
    }

    @Override
    public void setBlock( int x, int y, int z, BlockState blockState ) {
        Block block = ( (BlockStateDelegate) blockState ).getHandle();
        if ( this.chunk.getBlock( x, y, z, 0 ) instanceof BlockWaterlily || this.chunk.getBlock( x, y, z, 0 ) instanceof BlockWater || this.chunk.getBlock( x, y, z, 0 ) instanceof BlockFlowingWater ) {
            this.chunk.setBlock( x, y, z, 1, block );
        }
        this.chunk.setBlock( x, y, z, 0, block );
    }

    @Override
    public BlockState getBlock( int x, int y, int z ) {
        return TerraAdapter.adapt( this.chunk.getBlock( x, y, z, 0 ) );
    }

    @Override
    public Chunk getHandle() {
        return this.chunk;
    }
}
