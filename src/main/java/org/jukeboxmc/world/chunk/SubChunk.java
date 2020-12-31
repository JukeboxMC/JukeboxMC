package org.jukeboxmc.world.chunk;

import lombok.Getter;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockPalette;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.utils.Palette;
import org.jukeboxmc.utils.Utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SubChunk {

    @Getter
    private int y;
    private Integer[][] blocks;

    public SubChunk( int subChunkY ) {
        this.y = subChunkY;
        this.blocks = new Integer[Chunk.CHUNK_LAYERS][4096];
        for ( int layer = 0; layer < Chunk.CHUNK_LAYERS; layer++ ) {
            this.blocks[layer] = new Integer[4096];
            for ( int x = 0; x < 16; x++ ) {
                for ( int z = 0; z < 16; z++ ) {
                    for ( int y = 0; y < 16; y++ ) {
                        this.blocks[layer][this.getIndex( x, y, z )] = BlockType.AIR.getBlock().getRuntimeId();
                    }
                }
            }
        }
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        this.blocks[layer][this.getIndex( x, y, z )] = block.getRuntimeId();
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        return BlockPalette.RUNTIME_TO_BLOCK.get( this.blocks[layer][this.getIndex( x, y, z )] );
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

            // Prepare palette
            int amountOfBlocks = (int) Math.floor( 32 / numberOfBits );
            Palette palette = new Palette( binaryStream, amountOfBlocks, false );

            byte paletteWord = (byte) ( (byte) ( palette.getPaletteVersion().getVersionId() << 1 ) | 1 );
            binaryStream.writeByte( paletteWord );
            palette.addIndexIDs( blockIds );
            palette.finish();

            binaryStream.writeSignedVarInt( runtimeIds.size() );
            runtimeIds.forEach( binaryStream::writeSignedVarInt );
        }
    }

}
