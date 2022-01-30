package org.jukeboxmc.world.chunk;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockPalette;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Palette;

import java.util.Collection;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SubChunk {

    private static final int AIR_RUNTIME = BlockType.AIR.getBlock().getRuntimeId();

    @Getter
    private final int y;

    public Palette[] blocks;
    private final Int2ObjectMap<BlockEntity> blockEntitys;

    public SubChunk( int subChunkY ) {
        this.y = subChunkY;

        this.blocks = new Palette[Chunk.CHUNK_LAYERS];
        this.blockEntitys = new Int2ObjectOpenHashMap<>();
        for ( int layer = 0; layer < Chunk.CHUNK_LAYERS; layer++ ) {
            this.blocks[layer] = new Palette( AIR_RUNTIME );
        }
    }

    public void setBlock( int index, int layer, int runtimeId ) {
        this.blocks[layer].set( index, runtimeId );
    }

    public void setBlock( int x, int y, int z, int layer, int runtimeId ) {
        this.blocks[layer].set( Utils.getIndex( x, y, z ), runtimeId );
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        this.blocks[layer].set( Utils.getIndex( x, y, z ), block.getRuntimeId() );
    }

    public int getRuntimeId( int x, int y, int z, int layer ) {
        return this.blocks[layer].get( Utils.getIndex( x, y, z ) );
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        int runtimeId = this.blocks[layer].get( Utils.getIndex( x, y, z ) );
        return BlockPalette.RUNTIME_TO_BLOCK.get( runtimeId ).clone();
    }

    public void setBlockEntity( int x, int y, int z, BlockEntity blockEntity ) {
        this.blockEntitys.put( Utils.getIndex( x, y, z ), blockEntity );
    }

    public BlockEntity getBlockEntity( int x, int y, int z ) {
        return this.blockEntitys.get( Utils.getIndex( x, y, z ) );
    }

    public void removeBlockEntity( int x, int y, int z ) {
        this.blockEntitys.remove( Utils.getIndex( x, y, z ) );
    }

    public Collection<BlockEntity> getBlockEntitys() {
        return this.blockEntitys.values();
    }

    public void writeTo( BinaryStream binaryStream ) {
        binaryStream.writeByte( 8 );
        binaryStream.writeByte( Chunk.CHUNK_LAYERS );

        for ( int layer = 0; layer < Chunk.CHUNK_LAYERS; layer++ )
            this.blocks[layer].writeTo( binaryStream, true );
    }

}