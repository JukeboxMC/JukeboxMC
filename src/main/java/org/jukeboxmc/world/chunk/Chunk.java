package org.jukeboxmc.world.chunk;

import com.google.common.collect.ImmutableSet;
import com.nukkitx.nbt.NBTOutputStream;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import lombok.Synchronized;
import org.iq80.leveldb.WriteBatch;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.palette.Palette;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.NonStream;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Chunk {

    private static final Block BLOCK_AIR = Block.create( BlockType.AIR );
    public static final int CHUNK_LAYERS = 2;
    public static final int CHUNK_VERSION = 41;

    private final World world;
    private final Dimension dimension;
    private final int x;
    private final int z;
    private final int minY;
    private final int maxY;
    private final int fullHeight;
    private final Set<Entity> entities;
    private final SubChunk[] subChunks;
    private final short[] height;

    private boolean dirty;
    private ChunkState chunkState;
    private final Lock writeLock;
    private final Lock readLock;

    private final Set<ChunkLoader> loaders = Collections.newSetFromMap( new IdentityHashMap<>() );

    public Chunk( World world, Dimension dimension, int x, int z ) {
        this.world = world;
        this.dimension = dimension;
        this.x = x;
        this.z = z;
        this.minY = switch ( dimension ) {
            case OVERWORLD -> -64;
            case NETHER, THE_END -> 0;
        };
        this.maxY = switch ( dimension ) {
            case OVERWORLD -> 319;
            case NETHER -> 127;
            case THE_END -> 255;
        };
        this.fullHeight = Math.abs( this.minY ) + this.maxY + 1;
        this.entities = new LinkedHashSet<>();
        this.subChunks = new SubChunk[this.fullHeight >> 4];
        this.height = new short[16 * 16];
        this.chunkState = ChunkState.NEW;
        ReadWriteLock lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
    }

    public World getWorld() {
        return this.world;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    public int getMinY() {
        return this.minY;
    }

    public int getMaxY() {
        return this.maxY;
    }

    public int getFullHeight() {
        return this.fullHeight;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public void setDirty( boolean dirty ) {
        this.dirty = dirty;
    }

    public ChunkState getChunkState() {
        return this.chunkState;
    }

    public void setChunkState( ChunkState chunkState ) {
        this.chunkState = chunkState;
    }

    public boolean isGenerated() {
        return this.chunkState.ordinal() >= 1;
    }

    public boolean isPopulated() {
        return this.chunkState.ordinal() >= 2;
    }

    public boolean isFinished() {
        return this.chunkState.ordinal() >= 3;
    }

    public Lock getReadLock() {
        return this.readLock;
    }

    public Lock getWriteLock() {
        return this.writeLock;
    }

    @Synchronized ( "loaders" )
    public void addLoader( ChunkLoader chunkLoader ) {
        this.loaders.add( chunkLoader );
    }

    @Synchronized ( "loaders" )
    public void removeLoader( ChunkLoader chunkLoader ) {
        this.loaders.remove( chunkLoader );
    }

    @Synchronized ( "loaders" )
    public Set<ChunkLoader> getLoaders() {
        return ImmutableSet.copyOf( this.loaders );
    }

    public Set<Entity> getEntities() {
        return this.entities;
    }

    public SubChunk[] getSubChunks() {
        return this.subChunks;
    }

    public Collection<Player> getPlayers() {
        return this.entities.stream().filter( entity -> entity instanceof Player ).map( entity -> (Player) entity ).collect( Collectors.toSet() );
    }

    public void addEntity( Entity entity ) {
        this.entities.add( entity );
    }

    public void removeEntity( Entity entity ) {
        this.entities.removeIf( target -> target.getEntityId() == entity.getEntityId() );
    }

    public short[] getHeight() {
        return this.height;
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        this.writeLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) return;
            this.getOrCreateSubChunk( this.getSubY( y ) ).setBlock( x, y, z, layer, block );
        } finally {
            this.writeLock.unlock();
        }
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        this.readLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) {
                return BLOCK_AIR;
            }

            final int subY = this.getSubY( y );
            if ( this.subChunks[subY] == null ) {
                this.subChunks[subY] = new SubChunk( subY );
            }
            Block block = this.subChunks[subY].getBlock( x, y, z, layer );
            block.setLocation( new Location( this.world, x, y, z, this.dimension ) );
            block.setLayer( layer );
            return block;
        } finally {
            this.readLock.unlock();
        }
    }

    public void setBlockEntity( int x, int y, int z, BlockEntity blockEntity ) {
        this.writeLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) return;
            this.getOrCreateSubChunk( this.getSubY( y ) ).setBlockEntity( x, y, z, blockEntity );
        } finally {
            this.writeLock.unlock();
        }
    }

    public void removeBlockEntity( int x, int y, int z ) {
        this.writeLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) return;
            this.getOrCreateSubChunk( this.getSubY( y ) ).removeBlockEntity( x, y, z );
        } finally {
            this.writeLock.unlock();
        }
    }

    public BlockEntity getBlockEntity( int x, int y, int z ) {
        this.readLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) {
                return null;
            }
            final int subY = this.getSubY( y );
            if ( this.subChunks[subY] == null ) {
                this.subChunks[subY] = new SubChunk( subY );
            }
            return this.subChunks[subY].getBlockEntity( x, y, z );
        } finally {
            this.readLock.unlock();
        }
    }

    public Collection<BlockEntity> getBlockEntities() {
        List<BlockEntity> blockEntities = new ArrayList<>();
        for ( SubChunk subChunk : this.subChunks ) {
            if ( subChunk != null && subChunk.getBlockEntities() != null ) {
                blockEntities.addAll( subChunk.getBlockEntities() );
            }
        }
        return blockEntities;
    }

    public void setBiome( int x, int y, int z, Biome biome ) {
        this.writeLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) return;
            this.getOrCreateSubChunk( this.getSubY( y ) ).setBiome( x, y, z, biome );
        } finally {
            this.writeLock.unlock();
        }
    }

    public Biome getBiome( int x, int y, int z ) {
        this.readLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) return null;
            return this.getOrCreateSubChunk( this.getSubY( y ) ).getBiome( x, y, z );
        } finally {
            this.readLock.unlock();
        }
    }

    private boolean isHeightOutOfBounds( int y ) {
        return y < this.minY || y > this.maxY;
    }

    public SubChunk getOrCreateSubChunk( int subY ) {
        this.readLock.lock();
        try {
            for ( int y = 0; y <= subY; y++ ) {
                if ( this.subChunks[y] == null ) {
                    this.subChunks[y] = new SubChunk( y + ( Math.abs( this.minY ) >> 4 ) );
                }
            }
            return this.subChunks[subY];
        } finally {
            this.readLock.unlock();
        }
    }

    public int getSubY( int y ) {
        return ( y >> 4 ) + ( Math.abs( this.minY ) >> 4 );
    }

    public int getAvailableSubChunks() {
        return NonStream.sum( this.subChunks, o -> o == null ? 0 : 1 );
    }

    private void writeTo( ByteBuf byteBuf ) {
        Palette<Biome> lastBiomes = new Palette<>( Biome.PLAINS );

        for ( SubChunk subChunk : this.subChunks ) {
            if ( subChunk == null ) break;
            subChunk.writeToNetwork( byteBuf );
        }

        for ( SubChunk subChunk : this.subChunks ) {
            if ( subChunk == null ) {
                lastBiomes.writeToNetwork( byteBuf, Biome::getId, lastBiomes );
                continue;
            }

            subChunk.getBiomes().writeToNetwork( byteBuf, Biome::getId );
            lastBiomes = subChunk.getBiomes();
        }

        byteBuf.writeByte( 0 ); // edu - border blocks

        Collection<BlockEntity> blockEntities = this.getBlockEntities();
        if ( !blockEntities.isEmpty() ) {
            try ( NBTOutputStream writer = NbtUtils.createNetworkWriter( new ByteBufOutputStream( byteBuf ) ) ) {
                for ( BlockEntity blockEntity : blockEntities ) {
                    NbtMap tag = blockEntity.toCompound().build();
                    writer.writeTag( tag );
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    public LevelChunkPacket createLevelChunkPacket() {
        ByteBuf byteBuf = Unpooled.buffer();
        final LevelChunkPacket levelChunkPacket = new LevelChunkPacket();
        levelChunkPacket.setChunkX( this.x );
        levelChunkPacket.setChunkZ( this.z );
        levelChunkPacket.setCachingEnabled( false );
        levelChunkPacket.setRequestSubChunks( false );
        levelChunkPacket.setSubChunksLength( this.getAvailableSubChunks() );
        this.writeTo( byteBuf );

        byte[] data = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes( data );

        levelChunkPacket.setData( data );
        return levelChunkPacket;
    }

    public void saveChunkSlice( int subY, WriteBatch writeBatch ) {
        ByteBuf buffer = Unpooled.buffer();
        SubChunk subChunk = this.subChunks[this.dimension.equals( Dimension.OVERWORLD ) ? subY + 4 : subY];

        buffer.writeByte( (byte) subChunk.getSubChunkVersion() );
        buffer.writeByte( (byte) subChunk.getLayer() );
        buffer.writeByte( (byte) subY );

        for ( int layer = 0; layer < CHUNK_LAYERS; layer++ ) {
            subChunk.getBlocks()[layer].writeToStoragePersistent( buffer, value -> BlockPalette.getBlockNbt( value.getRuntimeId() ) );
        }

        byte[] subChunkKey = Utils.getSubChunkKey( this.x, this.z, this.dimension, (byte) 0x2f, (byte) subY );
        writeBatch.put( subChunkKey, Utils.array( buffer ) );
    }

    @Override
    public String toString() {
        return "X: " + this.x + "; Z: " + this.z;
    }
}
