package org.jukeboxmc.world.chunk;

import com.google.common.collect.ImmutableSet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Synchronized;
import org.cloudburstmc.nbt.NBTOutputStream;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtUtils;
import org.cloudburstmc.protocol.bedrock.packet.LevelChunkPacket;
import org.iq80.leveldb.WriteBatch;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.palette.Palette;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.NonStream;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Chunk {

    public static final int CHUNK_LAYERS = 2;
    public static final int CHUNK_VERSION = 40;
    public static final int SUB_CHUNK_VERSION = 9;
    private static final Block BLOCK_AIR = Block.create( BlockType.AIR );
    private final World world;
    private final Dimension dimension;
    private final int x;
    private final int z;
    private final int minY;
    private final int maxY;
    private final int fullHeight;
    private final Set<Entity> entities;
    private final Set<Player> players;
    private final Int2ObjectMap<BlockEntity> blockEntities;
    private final SubChunk[] subChunks;
    private final short[] height;
    private final Lock writeLock;
    private final Lock readLock;
    private final Set<ChunkLoader> loaders = Collections.newSetFromMap( new IdentityHashMap<>() );
    private boolean dirty;
    private ChunkState chunkState;

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
        this.entities = new CopyOnWriteArraySet<>();
        this.players = new CopyOnWriteArraySet<>();
        this.blockEntities = new Int2ObjectOpenHashMap<>();
        this.subChunks = new SubChunk[this.fullHeight >> 4];
        this.height = new short[16 * 16];
        this.chunkState = ChunkState.NEW;
        ReadWriteLock lock = new ReentrantReadWriteLock();
        this.writeLock = lock.writeLock();
        this.readLock = lock.readLock();
    }

    public Lock getWriteLock() {
        return writeLock;
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
        return this.players;
    }

    public void addEntity( Entity entity ) {
        this.entities.add( entity );
        if ( entity instanceof Player player ) {
            this.players.add( player );
        }
    }

    public void removeEntity( Entity entity ) {
        this.entities.removeIf( target -> target.getEntityId() == entity.getEntityId() );
        if ( entity instanceof Player player ) {
            this.players.removeIf( target -> target.getEntityId() == player.getEntityId() );
        }
    }

    public void setBlockEntity( int x, int y, int z, BlockEntity blockEntity ) {
        this.blockEntities.put( Utils.indexOf( x, y, z ), blockEntity );
        this.dirty = true;
    }

    public void removeBlockEntity( int x, int y, int z ) {
        this.blockEntities.remove( Utils.indexOf( x, y, z ) );
    }

    public BlockEntity getBlockEntity( int x, int y, int z ) {
        return this.blockEntities.get( Utils.indexOf( x, y, z ) );
    }

    public Collection<BlockEntity> getBlockEntities() {
        return this.blockEntities.values();
    }

    public short[] getHeight() {
        return this.height;
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        this.writeLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) return;
            this.getOrCreateSubChunk( this.getSubY( y ) ).setBlock( x, y, z, layer, block );
            this.dirty = true;
        } finally {
            this.writeLock.unlock();
        }
    }

    public void setBlock( Vector position, int layer, Block block ) {
        this.setBlock( position.getBlockX(), position.getBlockY(), position.getBlockZ(), layer, block );
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

    public int getHighestBlockY( int x, int z ) {
        int y;
        for ( y = this.getMaxY(); y > this.getMinY(); --y ) {
            BlockType blockType = this.getBlock( x, y, z, 0 ).getType();
            if ( blockType != BlockType.AIR ) {
                break;
            }
        }
        return ++y;
    }

    public Block getHighestBlock( int x, int z ) {
        for ( int y = this.getMaxY(); y > this.getMinY(); --y ) {
            Block block = this.getBlock( x, y, z, 0 );
            BlockType blockType = block.getType();
            if ( blockType != BlockType.AIR ) {
                return block;
            }
        }
        return null;
    }

    public void setBiome( int x, int y, int z, Biome biome ) {
        this.writeLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) return;
            this.getOrCreateSubChunk( this.getSubY( y ) ).setBiome( x, y, z, biome );
            this.dirty = true;
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
        return this.getOrCreateSubChunk( subY, false );
    }

    public SubChunk getOrCreateSubChunk( int subY, boolean lock ) {
        if ( lock ) this.writeLock.lock();
        try {
            for ( int y = 0; y <= subY; y++ ) {
                if ( this.subChunks[y] == null ) {
                    this.subChunks[y] = new SubChunk( y + ( Math.abs( this.minY ) >> 4 ) );
                }
            }
            return this.subChunks[subY];
        } finally {
            if ( lock ) this.writeLock.unlock();
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
        try {
            final LevelChunkPacket levelChunkPacket = new LevelChunkPacket();
            levelChunkPacket.setChunkX( this.x );
            levelChunkPacket.setChunkZ( this.z );
            levelChunkPacket.setCachingEnabled( false );
            levelChunkPacket.setRequestSubChunks( false );
            levelChunkPacket.setSubChunksLength( this.getAvailableSubChunks() );
            this.writeTo( byteBuf );
            levelChunkPacket.setData( byteBuf.retainedDuplicate() );
            return levelChunkPacket;
        } finally {
            byteBuf.release();
        }
    }

    public void saveChunkSlice( Palette<Block>[] blockPalettes, int subY, WriteBatch writeBatch ) {
        ByteBuf buffer = Unpooled.buffer();

        buffer.writeByte( (byte) SUB_CHUNK_VERSION );
        buffer.writeByte( (byte) blockPalettes.length );
        buffer.writeByte( (byte) subY );

        for ( Palette<Block> blockPalette : blockPalettes ) {
            blockPalette.writeToStoragePersistent( buffer, value -> BlockPalette.getBlockNbt( value.getRuntimeId() ) );
        }

        byte[] subChunkKey = Utils.getSubChunkKey( this.x, this.z, this.dimension, (byte) 0x2f, (byte) subY );
        writeBatch.put( subChunkKey, Utils.array( buffer ) );
    }

    @Override
    public String toString() {
        return "X: " + this.x + "; Z: " + this.z;
    }
}
