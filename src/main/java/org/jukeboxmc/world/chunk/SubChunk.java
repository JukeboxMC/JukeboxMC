package org.jukeboxmc.world.chunk;

import lombok.Getter;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockPalette;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.utils.Palette;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.World;

import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SubChunk {

    private static final int AIR_RUNTIME = BlockType.AIR.getBlock().getRuntimeId();

    @Getter
    private final int y;

    public Integer[][] blocks;
    private final Map<Integer, BlockEntity> blockEntitys;

    public SubChunk( int subChunkY ) {
        this.y = subChunkY;

        this.blocks = new Integer[Chunk.CHUNK_LAYERS][4096];
        this.blockEntitys = new HashMap<>();
        for ( int layer = 0; layer < Chunk.CHUNK_LAYERS; layer++ ) {
            this.blocks[layer] = new Integer[4096];
            for ( int x = 0; x < 16; x++ ) {
                for ( int z = 0; z < 16; z++ ) {
                    for ( int y = 0; y < 16; y++ ) {
                        this.blocks[layer][this.getIndex( x, y, z )] = AIR_RUNTIME;
                    }
                }
            }
        }
    }

    public void setBlock( int index, int layer, int runtimeId ) {
        this.blocks[layer][index] = runtimeId;
    }

    public void setBlock( int x, int y, int z, int layer, int runtimeId ) {
        this.blocks[layer][this.getIndex( x, y, z )] = runtimeId;
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        this.blocks[layer][this.getIndex( x, y, z )] = block.getRuntimeId();
    }

    public int getRuntimeId( int x, int y, int z, int layer ) {
        return this.blocks[layer][this.getIndex( x, y, z )];
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        int runtimeId = this.blocks[layer][this.getIndex( x, y, z )];
        return BlockPalette.RUNTIME_TO_BLOCK.get( runtimeId ).clone();
    }

    public void setBlockEntity( int x, int y, int z, BlockEntity blockEntity ) {
        this.blockEntitys.put( this.getIndex( x, y, z ), blockEntity );
    }

    public BlockEntity getBlockEntity( int x, int y, int z ) {
        return this.blockEntitys.get( this.getIndex( x, y, z ) );
    }

    public void removeBlockEntity( int x, int y, int z ) {
        this.blockEntitys.remove( this.getIndex( x, y, z ) );
    }

    public Collection<BlockEntity> getBlockEntitys() {
        return this.blockEntitys.values();
    }

    private int getIndex( int x, int y, int z ) {
        return ( x << 8 ) + ( z << 4 ) + y;
    }

    public void writeTo( BinaryStream binaryStream ) {
        binaryStream.writeByte( 8 );
        binaryStream.writeByte( Chunk.CHUNK_LAYERS );

        for ( int layer = 0; layer < Chunk.CHUNK_LAYERS; layer++ ) {
            Integer[] layerBlocks = this.blocks[layer];
            Integer foundIndex = 0;
            int nextIndex = 0;
            int lastRuntimeId = -1;

            int[] blockIds = new int[4096];
            Map<Integer, Integer> indexList = new LinkedHashMap<>();
            List<Integer> runtimeIds = new ArrayList<>();

            for ( short blockIndex = 0; blockIndex < layerBlocks.length; blockIndex++ ) {
                int runtimeId = layerBlocks[blockIndex];
                if ( runtimeId != lastRuntimeId ) {
                    foundIndex = indexList.get( runtimeId );
                    if ( foundIndex == null ) {
                        runtimeIds.add( runtimeId );
                        indexList.put( runtimeId, nextIndex );
                        foundIndex = nextIndex;
                        nextIndex++;
                    }

                    lastRuntimeId = runtimeId;
                }

                blockIds[blockIndex] = foundIndex;
            }

            float numberOfBits = Utils.log2( indexList.size() ) + 1;

            int amountOfBlocks = (int) Math.floor( 32 / numberOfBits );
            Palette palette = new Palette( binaryStream, amountOfBlocks, false );

            byte paletteWord = (byte) ( (byte) ( palette.getPaletteVersion().getVersionId() << 1 ) | 1 );
            binaryStream.writeByte( paletteWord );
            palette.addIndexIDs( blockIds );
            palette.finish();

            binaryStream.writeSignedVarInt( runtimeIds.size() );

            for ( Integer runtimeId : runtimeIds ) {
                binaryStream.writeSignedVarInt( runtimeId );
            }
        }
    }

}