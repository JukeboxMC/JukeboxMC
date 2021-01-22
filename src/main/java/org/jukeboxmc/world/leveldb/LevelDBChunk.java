package org.jukeboxmc.world.leveldb;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import org.jukeboxmc.block.BlockPalette;
import org.jukeboxmc.nbt.NBTInputStream;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtUtils;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.utils.Palette;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.SubChunk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LevelDBChunk {

    private World world;
    private int chunkX;
    private int chunkZ;
    protected byte chunkVersion;
    protected boolean populated;

    protected byte[] biomes = new byte[16 * 16];
    protected short[] height = new short[16 * 16];

    public LevelDBChunk( World world, int chunkX, int chunkZ, byte chunkVersion, boolean populated ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.chunkVersion = chunkVersion;
        this.populated = populated;
    }

    public LevelDBChunk( World world, int chunkX, int chunkZ ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.populated = true;
    }

    public void load( SubChunk chunk, byte[] chunkData ) {
        BinaryStream buffer = new BinaryStream( Utils.allocate( chunkData ) );
        byte subchunkVersion = buffer.readByte();
        int storages = 1;
        switch ( subchunkVersion ) {
            case 8:
                storages = buffer.readByte();
            case 1:
                for ( int layer = 0; layer < storages; layer++ ) {
                    byte data = buffer.readByte();
                    boolean isPersistent = ( ( data >> 8 ) & 1 ) != 1;
                    byte wordTemplate = (byte) ( data >>> 1 );

                    Palette palette = new Palette( buffer, wordTemplate, true );
                    short[] indexes = palette.getIndexes();
                    int needed = buffer.readLInt();

                    Map<Short, Integer> chunkPalette = new HashMap<>();
                    short i = 0;
                    NBTInputStream reader = NbtUtils.createReaderLE( new ByteBufInputStream( buffer.getBuffer() ) );
                    while ( i < needed ) {
                        try {
                            NbtMap compound = (NbtMap) reader.readTag();
                            String identifier = compound.getString( "name" );
                            NbtMap states = compound.getCompound( "states" );
                            if ( states != null ) {
                                chunkPalette.put( i++, BlockPalette.getRuntimeId( identifier, states ) );
                            } else {
                                chunkPalette.put( i++, BlockPalette.getRuntimeId( identifier, NbtMap.EMPTY ) );
                            }
                        } catch ( IOException e ) {
                            e.printStackTrace();
                            break;
                        }
                    }

                    for ( short index = 0; index < indexes.length; index++ ) {
                        chunk.blocks[layer][index] = chunkPalette.get( indexes[index] );
                    }
                }
                break;
        }
    }

    public void loadHeightAndBiomes( byte[] heightAndBiomes ) {
        BinaryStream stream = new BinaryStream( Unpooled.wrappedBuffer( heightAndBiomes ) );
        for ( int i = 0; i < this.height.length; i++ ) {
            this.height[i] = (short) stream.readUnsignedShort();
        }
        System.arraycopy( heightAndBiomes, 512, this.biomes, 0, 256 );
    }

    public void setPopulated( boolean populated ) {
        this.populated = populated;
    }
}
