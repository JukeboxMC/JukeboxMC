package org.jukeboxmc.world.chunk;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAir;
import org.jukeboxmc.block.BlockPalette;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.palette.integer.IntPalette;

import java.util.Collection;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SubChunk {

    private static final int AIR_RUNTIME_ID = new BlockAir().getRuntimeId();
    public static final int CHUNK_LAYERS = 2;


    private final int subChunkY;
    private int subChunkVersion = 9;
    private int layer = 1;

    public final IntPalette[] blocks;
    private final Int2ObjectMap<BlockEntity> blockEntities;

    public SubChunk( int subChunkY ) {
        this.subChunkY = subChunkY;

        this.blocks = new IntPalette[CHUNK_LAYERS];
        for ( int layer = 0; layer < CHUNK_LAYERS; layer++ ) {
            this.blocks[layer] = new IntPalette( AIR_RUNTIME_ID );
        }

        this.blockEntities = new Int2ObjectOpenHashMap<>();
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

    public Block getBlock( int x, int y, int z, int layer ) {
        return BlockPalette.RUNTIME_TO_BLOCK.get( this.blocks[layer].get( Utils.getIndex( x, y, z ) ) ).clone();
    }

    public void setBlockEntity( int x, int y, int z, BlockEntity blockEntity ) {
        this.blockEntities.put( Utils.getIndex( x, y, z ), blockEntity );
    }

    public BlockEntity getBlockEntity( int x, int y, int z ) {
        return this.blockEntities.get( Utils.getIndex( x, y, z ) );
    }

    public void removeBlockEntity( int x, int y, int z ) {
        this.blockEntities.remove( Utils.getIndex( x, y, z ) );
    }

    public Collection<BlockEntity> getBlockEntities() {
        return this.blockEntities.values();
    }

    public int getRuntimeId( int x, int y, int z, int layer ) {
        return this.blocks[layer].get( Utils.getIndex( x, y, z ) );
    }

    public void writeToNetwork( ByteBuf byteBuf ) {
        byteBuf.writeByte( this.subChunkVersion );
        byteBuf.writeByte( this.layer );
        byteBuf.writeByte( this.subChunkY );

        for ( int layer = 0; layer < this.layer; layer++ ) {
            this.blocks[layer].writeToNetwork( byteBuf, runtimeId -> runtimeId );
        }
    }

    public int getSubChunkVersion() {
        return this.subChunkVersion;
    }

    public void setSubChunkVersion( int subChunkVersion ) {
        this.subChunkVersion = subChunkVersion;
    }

    public int getLayer() {
        return this.layer;
    }

    public void setLayer( int layer ) {
        this.layer = layer;
    }
}
