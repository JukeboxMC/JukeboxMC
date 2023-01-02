package org.jukeboxmc.world.chunk;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.palette.Palette;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.world.Biome;

import java.util.Collection;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SubChunk {

    private final int y;
    private int layer = 1;
    private int subChunkVersion = 9;
    private final Palette<Biome> biomes;
    private final Palette<Block>[] blocks;
    private final Int2ObjectMap<BlockEntity> blockEntities;

    public SubChunk( int subChunkY ) {
        this.y = subChunkY;
        this.biomes = new Palette<>( Biome.PLAINS );
        this.blocks = new Palette[Chunk.CHUNK_LAYERS];
        for ( int layer = 0; layer < Chunk.CHUNK_LAYERS; layer++ ) {
            this.blocks[layer] = new Palette<>( Block.create( BlockType.AIR ) );
        }
        this.blockEntities = new Int2ObjectOpenHashMap<>();
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        this.blocks[layer].set( this.indexOf( x, y, z ), block );
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        return this.blocks[layer].get( this.indexOf( x, y, z ) ).clone();
    }

    public void setBiome( int x, int y, int z, Biome biome ) {
        this.biomes.set( this.indexOf( x, y, z ), biome );
    }

    public void setBlockEntity( int x, int y, int z, BlockEntity blockEntity ) {
        this.blockEntities.put( this.indexOf( x, y, z ), blockEntity );
    }

    public void removeBlockEntity( int x, int y, int z ) {
        this.blockEntities.remove( this.indexOf( x, y, z ) );
    }

    public BlockEntity getBlockEntity( int x, int y, int z ) {
        return this.blockEntities.get( this.indexOf( x, y, z ) );
    }

    public Collection<BlockEntity> getBlockEntities() {
        return this.blockEntities.values();
    }

    public Biome getBiome( int x, int y, int z ) {
        return this.biomes.get( this.indexOf( x, y, z ) );
    }

    public Palette<Biome> getBiomes() {
        return this.biomes;
    }

    public Palette<Block>[] getBlocks() {
        return this.blocks;
    }

    public int getLayer() {
        return this.layer;
    }

    public void setLayer( int layer ) {
        this.layer = layer;
    }

    public int getSubChunkVersion() {
        return this.subChunkVersion;
    }

    public void setSubChunkVersion( int subChunkVersion ) {
        this.subChunkVersion = subChunkVersion;
    }

    public int getY() {
        return this.y;
    }

    private int indexOf( int x, int y, int z ) {
        return ( ( x & 15 ) << 8 ) + ( ( z & 15 ) << 4 ) + ( y & 15 );
    }

    public void writeToNetwork( ByteBuf byteBuf ) {
        byteBuf.writeByte( this.subChunkVersion );
        byteBuf.writeByte( this.blocks.length );
        byteBuf.writeByte( this.y );

        for ( Palette<Block> blockPalette : this.blocks ) {
            blockPalette.writeToNetwork( byteBuf, Block::getRuntimeId );
        }
    }
}
