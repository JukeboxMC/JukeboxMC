package org.jukeboxmc.world.leveldb;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockPalette;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.nbt.NBTInputStream;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtUtils;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Palette;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.SubChunk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class LevelDBChunk {

    protected boolean populated = true;

    protected Palette[] biomes = new Palette[24];
    protected short[] height = new short[16 * 16];

    protected LevelDBChunk() {
    }

    public void loadSection( SubChunk chunk, byte[] chunkData ) {
        BinaryStream buffer = new BinaryStream( Utils.allocate( chunkData ) );
        byte subChunkVersion = buffer.readByte();
        int storages = 1;
        switch ( subChunkVersion ) {
            case 9:
            case 8:
                storages = buffer.readByte();
                if ( subChunkVersion == 9 ) {
                    buffer.readByte(); // same as sub chunk height
                }
            case 1:
                for ( int layer = 0; layer < storages; layer++ ) {
                    byte data = buffer.readByte();
                    boolean persistent = ( ( data >> 8 ) & 1 ) != 1;
                    byte wordTemplate = (byte) ( data >>> 1 );

                    short[] indices = Palette.parseIndices( buffer, wordTemplate );
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

                    for ( short index = 0; index < indices.length; index++ ) {
                        chunk.blocks[layer].set( index, chunkPalette.get( indices[index] ) );
                    }
                }
                break;
        }
    }

    public void loadBlockEntities( Chunk chunk, byte[] blockEntityData ) {
        ByteBuf data = Utils.allocate( blockEntityData );

        NBTInputStream reader = NbtUtils.createReaderLE( new ByteBufInputStream( data ) );
        while ( data.readableBytes() > 0 ) {
            try {
                NbtMap nbtMap = (NbtMap) reader.readTag();

                int x = nbtMap.getInt( "x", 0 );
                int y = nbtMap.getInt( "y", 0 );
                int z = nbtMap.getInt( "z", 0 );

                Block block = chunk.getBlock( x, y, z, 0 );
                if ( block != null && block.hasBlockEntity() ) {
                    BlockEntity blockEntity = BlockEntityType.getBlockEntityById( nbtMap.getString( "id" ), block );
                    if ( blockEntity != null ) {
                        blockEntity.setCompound( nbtMap );
                        chunk.setBlockEntity( x, y, z, blockEntity );
                    }
                }
            } catch ( IOException e ) {
                break;
            }
        }
    }

    public void loadHeightAndBiomes( byte[] heightAndBiomes ) {
        BinaryStream stream = new BinaryStream( Unpooled.wrappedBuffer( heightAndBiomes ) );
        for ( int i = 0; i < this.height.length; i++ ) {
            this.height[i] = stream.readLShort();
        }

        Palette last = null;
        int paletteVer;
        int biomeHeader;
        Palette biomePalette;

        for ( int y = -4; y < this.biomes.length - 4; y++ ) {
            biomePalette = this.getBiomePalette( y << 4 );
            biomeHeader = stream.readByte() & 0xFF;
            paletteVer = biomeHeader >> 1;

            if ( paletteVer == 0x7F ) {
                if ( last != null ) {
                    last.copyTo( biomePalette );
                }

                continue;
            }

            last = biomePalette;

            if ( paletteVer == 0 ) {
                biomePalette.setFirst( stream.readLInt() );
                continue;
            }

            short[] indices = Palette.parseIndices( stream, paletteVer );
            int[] biomeIds = new int[stream.readLInt()];

            for ( int i = 0; i < biomeIds.length; i++ ) {
                biomeIds[i] = stream.readLInt();
            }

            for ( int i = 0; i < indices.length; i++ ) {
                biomePalette.set( i, biomeIds[indices[i]] );
            }
        }
    }

    public abstract Palette getBiomePalette( int y );

    public void setPopulated( boolean populated ) {
        this.populated = populated;
    }

    public void setHeight( short[] height ) {
        this.height = height;
    }

    public short[] getHeight() {
        return this.height;
    }
}
