package org.jukeboxmc.world.leveldb;

import com.nukkitx.nbt.NBTInputStream;
import com.nukkitx.nbt.NBTOutputStream;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.palette.Palette;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntityRegistry;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.ChunkState;
import org.jukeboxmc.world.chunk.SubChunk;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LevelDB {

    private final World world;
    private final DB db;

    public LevelDB(@NotNull World world ) {
        this.world = world;
        try {
            Options options = new Options()
                    .createIfMissing( true )
                    .compressionType( CompressionType.ZLIB_RAW )
                    .blockSize( 64 * 1024 );
            this.db = net.daporkchop.ldbjni.LevelDB.PROVIDER.open( new File( world.getWorldFolder(), "db" ), options );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    public @NotNull CompletableFuture<Chunk> readChunk(@NotNull Chunk chunk ) {
        return CompletableFuture.supplyAsync( () -> {
            byte[] version = this.db.get( Utils.getKey( chunk.getX(), chunk.getZ(), chunk.getDimension(), (byte) 0x2C ) );
            if ( version == null ) {
                version = this.db.get( Utils.getKey( chunk.getX(), chunk.getZ(), chunk.getDimension(), (byte) 0x76 ) );
            }

            if ( version == null ) {
                return null;
            }

            byte[] finalized = this.db.get( Utils.getKey( chunk.getX(), chunk.getZ(), chunk.getDimension(), (byte) 0x36 ) );
            if ( finalized == null ) {
                chunk.setChunkState( ChunkState.FINISHED );
            } else {
                chunk.setChunkState( ChunkState.values()[Unpooled.wrappedBuffer( finalized ).readIntLE() + 1] );
            }

            for ( int subChunkIndex = chunk.getMinY() >> 4; subChunkIndex < chunk.getMaxY() >> 4; subChunkIndex++ ) {
                byte[] chunkData = this.db.get( Utils.getSubChunkKey( chunk.getX(), chunk.getZ(), chunk.getDimension(), (byte) 0x2f, (byte) subChunkIndex ) );
                final int arrayIndex = subChunkIndex + ( Math.abs( chunk.getMinY() ) >> 4 );

                if ( chunkData != null ) {
                    this.loadSection( chunk.getOrCreateSubChunk( arrayIndex, true ), chunkData );
                }
            }

            byte[] heightAndBiomes = this.db.get( Utils.getKey( chunk.getX(), chunk.getZ(), chunk.getDimension(), (byte) 0x2b ) );
            if ( heightAndBiomes != null ) {
                this.loadHeightAndBiomes( chunk, heightAndBiomes );
            }

            byte[] blockEntities = this.db.get( Utils.getKey( chunk.getX(), chunk.getZ(), chunk.getDimension(), (byte) 0x31 ) );
            if ( blockEntities != null ) {
                this.loadBlockEntities( chunk, blockEntities );
            }

            return chunk;
        }, Server.getInstance().getScheduler().getChunkExecutor() );
    }

    private void loadSection(@NotNull SubChunk chunk, byte @NotNull [] chunkData ) {
        ByteBuf buffer = Unpooled.wrappedBuffer( chunkData );
        try {
            byte subChunkVersion = buffer.readByte();
            chunk.setSubChunkVersion( subChunkVersion );
            int storages = 1;
            switch ( subChunkVersion ) {
                case 9:
                case 8:
                    storages = buffer.readByte();
                    chunk.setLayer( storages );
                    if ( subChunkVersion == 9 ) {
                        byte subY = buffer.readByte();
                    }
                case 1:
                    for ( int layer = 0; layer < storages; layer++ ) {
                        try {
                            buffer.markReaderIndex();
                            chunk.getBlocks()[layer].readFromStoragePersistent( buffer, compound -> {
                                String identifier = compound.getString( "name" );
                                NbtMap states = compound.getCompound( "states" );
                                return BlockPalette.getBlock( Identifier.fromString( identifier ), states );
                            } );
                        } catch ( IllegalArgumentException e ) {
                            buffer.resetReaderIndex();
                            chunk.getBlocks()[layer].readFromStorageRuntime( buffer, runtimeId -> BlockPalette.getBlockByNBT( BlockPalette.getBlockNbt( runtimeId ) ), null );
                        }
                    }
                    break;
            }
        } finally {
            buffer.release();
        }
    }

    private void loadHeightAndBiomes(@NotNull Chunk chunk, byte @NotNull [] heightAndBiomes ) {
        ByteBuf buffer = Unpooled.wrappedBuffer( heightAndBiomes );
        try {
            short[] height = chunk.getHeight();
            for ( int i = 0; i < height.length; i++ ) {
                height[i] = buffer.readShortLE();
            }

            if ( buffer.readableBytes() <= 0 ) return;

            Palette<Biome> last = null;
            Palette<Biome> biomePalette;
            for ( int y = chunk.getMinY() >> 4; y < ( chunk.getMaxY() + 1 ) >> 4; y++ ) {
                try {
                    biomePalette = chunk.getOrCreateSubChunk( chunk.getSubY( y << 4 ) ).getBiomes();
                    biomePalette.readFromStorageRuntime( buffer, Biome::findById, last );
                    last = biomePalette;
                } catch ( Exception ignored ) {
                }
            }
        } finally {
            buffer.release();
        }
    }

    private void loadBlockEntities(@NotNull Chunk chunk, byte @NotNull [] blockEntityData ) {
        ByteBuf byteBuf = Unpooled.wrappedBuffer( blockEntityData );

        try {
            NBTInputStream reader = NbtUtils.createReaderLE( new ByteBufInputStream( byteBuf ) );
            while ( byteBuf.readableBytes() > 0 ) {
                try {
                    NbtMap nbtMap = (NbtMap) reader.readTag();

                    int x = nbtMap.getInt( "x", 0 );
                    int y = nbtMap.getInt( "y", 0 );
                    int z = nbtMap.getInt( "z", 0 );

                    Block block = chunk.getBlock( x, y, z, 0 );

                    if ( block != null ) {
                        BlockEntityType blockEntityType = BlockEntityRegistry.getBlockEntityTypeById( nbtMap.getString( "id" ) );
                        if ( blockEntityType != null ) {
                            BlockEntity blockEntity = BlockEntity.create( blockEntityType, block );
                            if ( blockEntity != null ) {
                                blockEntity.fromCompound( nbtMap );
                                chunk.setBlockEntity( x, y, z, blockEntity );
                                blockEntity.setSpawned( true );
                            }
                        }
                    }
                } catch ( IOException e ) {
                    break;
                }
            }
        } finally {
            byteBuf.release();
        }
    }

    public @NotNull CompletableFuture<Void> saveChunk(@NotNull Chunk chunk ) {
        return CompletableFuture.supplyAsync( () -> {
            if ( !chunk.isGenerated() ) {
                return null;
            }
            try ( WriteBatch writeBatch = this.db.createWriteBatch() ) {
                //Write subChunks
                final int minY = chunk.getMinY() >> 4;
                for ( int subY = 0; subY < chunk.getAvailableSubChunks(); subY++ ) {
                    if ( chunk.getSubChunks()[subY] == null ) {
                        continue;
                    }
                    final int subChunkIndex = subY + minY;
                    chunk.saveChunkSlice( chunk.getSubChunks()[subY].getBlocks(), subChunkIndex, writeBatch );
                }

                //Write chunkVersion
                byte[] chunkVersion = Utils.getKey( chunk.getX(), chunk.getZ(), chunk.getDimension(), (byte) 0x2c );
                writeBatch.put( chunkVersion, new byte[]{Chunk.CHUNK_VERSION} );

                //Write blockEntities
                byte[] blockEntitiesKey = Utils.getKey( chunk.getX(), chunk.getZ(), chunk.getDimension(), (byte) 0x31 );
                ByteBuf buffer = Unpooled.buffer();
                Collection<BlockEntity> blockEntities = chunk.getBlockEntities();
                if ( !blockEntities.isEmpty() ) {
                    try ( NBTOutputStream networkWriter = NbtUtils.createWriterLE( new ByteBufOutputStream( buffer ) ) ) {
                        for ( BlockEntity blockEntity : blockEntities ) {
                            try {
                                NbtMap build = blockEntity.toCompound().build();
                                networkWriter.writeTag( build );
                            } catch ( IOException e ) {
                                e.printStackTrace();
                            }
                        }
                    } catch ( IOException e ) {
                        e.printStackTrace();
                    }

                    if ( buffer.readableBytes() > 0 ) {
                        writeBatch.put( blockEntitiesKey, Utils.array( buffer ) );
                    }
                    buffer.release();
                }

                //Write biomeAndHeight
                byte[] biomeAndHeight = Utils.getKey( chunk.getX(), chunk.getZ(), chunk.getDimension(), (byte) 0x2b );
                ByteBuf heightAndBiomesBuffer = Unpooled.buffer();
                for ( short height : chunk.getHeight() ) {
                    heightAndBiomesBuffer.writeShortLE( height );
                }

                Palette<Biome> last = null;
                Palette<Biome> biomePalette;
                for ( int y = chunk.getMinY() >> 4; y < ( chunk.getMaxY() + 1 ) >> 4; y++ ) {
                    biomePalette = chunk.getOrCreateSubChunk( chunk.getSubY( y << 4 ) ).getBiomes();
                    biomePalette.writeToStorageRuntime( heightAndBiomesBuffer, Biome::getId, last );
                    last = biomePalette;
                }
                writeBatch.put( biomeAndHeight, Utils.array( heightAndBiomesBuffer ) );

                this.db.write( writeBatch );
                return null;
            } catch ( IOException e ) {
                throw new RuntimeException( e );
            }
        }, Server.getInstance().getScheduler().getChunkExecutor() );
    }

    public void close() {
        try {
            this.db.close();
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }
}
