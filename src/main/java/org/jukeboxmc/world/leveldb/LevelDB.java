package org.jukeboxmc.world.leveldb;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import lombok.experimental.UtilityClass;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockPalette;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.nbt.NBTInputStream;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtUtils;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.SubChunk;
import org.jukeboxmc.world.palette.object.ObjectPalette;

import java.io.IOException;
import java.util.Objects;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@UtilityClass
public class LevelDB {

    public void loadSection( SubChunk chunk, byte[] chunkData ) {
        BinaryStream buffer = new BinaryStream( Utils.allocate( chunkData ) );
        try {
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
                        try {
                            buffer.getBuffer().markReaderIndex();
                            chunk.blocks[layer].readFromStoragePersistent( buffer, compound -> {
                                String identifier = compound.getString( "name" );
                                NbtMap states = compound.getCompound( "states" );
                                return BlockPalette.getRuntimeId( identifier, Objects.requireNonNullElse( states, NbtMap.EMPTY ) );
                            } );
                        } catch ( IllegalArgumentException e ) {
                            buffer.getBuffer().resetReaderIndex();
                            chunk.blocks[layer].readFromStorageRuntime( buffer, runtimeId -> runtimeId, null );
                        }
                    }
                    break;
            }
        } finally {
            buffer.release();
        }
    }

    public void loadBlockEntities( Chunk chunk, byte[] blockEntityData ) {
        ByteBuf data = Utils.allocate( blockEntityData );

        try {
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
                            blockEntity.fromCompound( nbtMap );
                            chunk.setBlockEntity( x, y, z, blockEntity );
                        }
                    }
                } catch ( IOException e ) {
                    break;
                }
            }
        } finally {
            data.release();
        }
    }

    public void loadHeightAndBiomes( Chunk chunk, byte[] heightAndBiomes ) {
        BinaryStream buffer = new BinaryStream( Unpooled.wrappedBuffer( heightAndBiomes ) );
        try {
            short[] height = chunk.getHeight();
            for ( int i = 0; i < height.length; i++ ) {
                height[i] = buffer.readLShort();
            }

            if(buffer.readableBytes() <= 0) return;

            int minSectionY = chunk.getMinY() >> 4;
            int fullHeight = Math.abs( chunk.getMinY() ) + Math.abs( chunk.getMaxY() ) + 1;
            ObjectPalette<Biome> last = null;
            ObjectPalette<Biome> biomePalette;
            for ( int y = 0; y < fullHeight >> 4; y++ ) {
                biomePalette = chunk.getBiomePalette( ( y + minSectionY ) << 4 );

                biomePalette.readFromStorageRuntime( buffer, Biome::findById, last );

                last = biomePalette;
            }
        } finally {
            buffer.release();
        }
    }

}
