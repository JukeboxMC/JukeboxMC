package org.jukeboxmc.world.chunk;

import com.nukkitx.nbt.NBTOutputStream;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAir;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.NonStream;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.palette.object.ObjectPalette;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Chunk {

    private static final Block BLOCK_AIR = new BlockAir();

    private final World world;
    private final int chunkX;
    private final int chunkZ;
    private final Dimension dimension;
    private final int fullHeight;
    private final short[] height;
    private final SubChunk[] subChunks;

    private boolean initiating = true;
    private boolean generated = false;
    private boolean populated = false;

    private final Lock readLock;
    private final Lock writeLock;

    private final Set<Entity> entities;
    private final ObjectPalette<Biome>[] biomes;

    public Chunk( World world, int chunkX, int chunkZ, Dimension dimension ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.dimension = dimension;

        this.fullHeight = Math.abs( this.getMinY() ) + Math.abs( this.getMaxY() ) + 1;
        this.subChunks = new SubChunk[fullHeight >> 4];
        /*
        for ( int y = this.getMinY(); y < this.getMaxY(); y++ ) {
            int subY = this.getSubY( y );
            this.subChunks[subY] = new SubChunk( subY );
        }
         */
        this.height = new short[16 * 16];
        this.biomes = new ObjectPalette[fullHeight >> 4];
        /*
        for ( int y = this.getMinY(); y < this.getMaxY(); y++ ) {
            int subY = this.getSubY( y );
            this.biomes[subY] = new ObjectPalette<>( Biome.PLAINS );
        }
         */

        ReadWriteLock lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();

        this.entities = new HashSet<>();
    }

    public World getWorld() {
        return this.world;
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public int getFullHeight() {
        return this.fullHeight;
    }

    public short[] getHeight() {
        return this.height;
    }

    public boolean isInitiating() {
        return this.initiating;
    }

    public void setInitiating( boolean initiating ) {
        this.initiating = initiating;
    }

    public boolean isGenerated() {
        return this.generated;
    }

    public void setGenerated( boolean generated ) {
        this.generated = generated;
    }

    public boolean isPopulated() {
        return this.populated;
    }

    public void setPopulated( boolean populated ) {
        this.populated = populated;
    }

    public void addEntity( Entity entity ) {
        this.entities.add( entity );
    }

    public void removeEntity( Entity entity ) {
        this.entities.remove( entity );
    }

    public Collection<Entity> getEntities() {
        return this.entities;
    }

    public Collection<Player> getPlayers() {
        Set<Player> players = new HashSet<>();
        for ( Entity entity : this.entities.stream().toList() ) {
            if ( entity instanceof Player player ) {
                players.add( player );
            }
        }
        return players;
    }

    public int getMinY() {
        return this.dimension == Dimension.OVERWORLD ? -64 : 0;
    }

    public int getMaxY() {
        return switch ( this.dimension ) {
            case OVERWORLD -> 319;
            case NETHER -> 127;
            case THE_END -> 255;
        };
    }

    private boolean isHeightOutOfBounds( int y ) {
        return y < this.getMinY() || y > this.getMaxY();
    }

    public int getAvailableSubChunks() {
        return NonStream.sum( this.subChunks, o -> o == null ? 0 : 1 );
    }

    public SubChunk getSubChunk( int y ) {
        this.writeLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) {
                return null;
            }

            int subY = this.getSubY( y );
            for ( int y0 = 0; y0 <= subY; y0++ ) {
                if ( this.subChunks[y0] == null ) {
                    this.biomes[y0] = new ObjectPalette<>( Biome.PLAINS );
                    this.subChunks[y0] = new SubChunk( y0 );
                }
            }

            return this.subChunks[subY];
        } finally {
            this.writeLock.unlock();
        }
    }

    public int getSubY( int y ) {
        return ( y >> 4 ) + ( Math.abs( this.getMinY() ) >> 4 );
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        this.readLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) {
                return BLOCK_AIR;
            }

            int subY = this.getSubY( y );
            if ( this.subChunks[subY] == null ) {
                return BLOCK_AIR;
            }

            Block block = this.subChunks[subY].getBlock( x, y, z, layer );
            block.setLocation( new Location( this.world, x, y, z ) );
            block.setLayer( layer );
            return block;
        } finally {
            this.readLock.unlock();
        }
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        this.writeLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) {
                return;
            }
            this.getSubChunk( y ).setBlock( x, y, z, layer, block );
        } finally {
            this.writeLock.unlock();
        }
    }

    public void setBlock( Vector vector, int layer, int runtimeId ) {
        this.writeLock.lock();
        try {
            if ( this.isHeightOutOfBounds( vector.getBlockY() ) ) {
                return;
            }
            this.getSubChunk( vector.getBlockY() ).setBlock( vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), layer, runtimeId );
        } finally {
            this.writeLock.unlock();
        }
    }

    public void setBlock( Vector vector, int layer, Block block ) {
        this.setBlock( vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), layer, block );
    }

    public Biome getBiome( int x, int y, int z ) {
        this.readLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) {
                return null;
            }
            return this.getBiomePalette( y ).get( Utils.getIndex( x, y, z ) );
        } finally {
            this.readLock.unlock();
        }
    }

    public void setBiome( int x, int y, int z, Biome biome ) {
        this.writeLock.lock();
        try {
            if ( this.isHeightOutOfBounds( y ) ) {
                return;
            }
            this.getBiomePalette( y ).set( Utils.getIndex( x, y, z ), biome );
        } finally {
            this.writeLock.unlock();
        }
    }

    public ObjectPalette<Biome> getBiomePalette( int y ) {
        if ( this.isHeightOutOfBounds( y ) ) {
            return null;
        }

        int subY = this.getSubY( y );
        for ( int y0 = 0; y0 <= subY; y0++ ) {
            if ( this.biomes[y0] == null ) {
                this.biomes[y0] = new ObjectPalette<>( Biome.PLAINS );
                this.subChunks[y0] = new SubChunk( y0 );
            }
        }

        return this.biomes[subY];
    }

    public BlockEntity getBlockEntity( int x, int y, int z ) {
        if ( y < -64 || y > 319 ) {
            return null;
        }
        return this.getSubChunk( y ).getBlockEntity( x, y, z );
    }

    public void setBlockEntity( int x, int y, int z, BlockEntity blockEntity ) {
        if ( y < -64 || y > 319 ) {
            return;
        }
        this.getSubChunk( y ).setBlockEntity( x, y, z, blockEntity );
    }

    public void removeBlockEntity( int x, int y, int z ) {
        if ( y < -64 || y > 319 ) {
            return;
        }
        this.getSubChunk( y ).removeBlockEntity( x, y, z );
    }

    public List<BlockEntity> getBlockEntities() {
        List<BlockEntity> blockEntities = new ArrayList<>();
        for ( SubChunk subChunk : this.subChunks ) {
            if ( subChunk != null && subChunk.getBlockEntities() != null ) {
                blockEntities.addAll( subChunk.getBlockEntities() );
            }
        }
        return blockEntities;
    }

    private void writeTo( ByteBuf buffer ) {
        for ( SubChunk subChunk : this.subChunks ) {
            if ( subChunk != null ) {
                subChunk.writeToNetwork( buffer );
            }
        }

        for ( ObjectPalette<Biome> biomePalette : this.biomes ) {
            if ( biomePalette == null ) {
                break;
            }
            biomePalette.writeToNetwork( buffer, Biome::getId );
        }

        buffer.writeByte( 0 ); // education edition - border blocks

        List<BlockEntity> blockEntities = this.getBlockEntities();
        if ( !blockEntities.isEmpty() ) {
            try ( NBTOutputStream writer = NbtUtils.createNetworkWriter( new ByteBufOutputStream( buffer ) ) ) {
                for ( BlockEntity blockEntity : blockEntities ) {
                    NbtMap tag = blockEntity.toCompound().build();
                    writer.writeTag( tag );
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    public synchronized LevelChunkPacket createLevelChunkPacket() {
        ByteBuf byteBuf = Unpooled.buffer();

        try {
            LevelChunkPacket levelChunkPacket = new LevelChunkPacket();
            levelChunkPacket.setChunkX( this.chunkX );
            levelChunkPacket.setChunkZ( this.chunkZ );
            levelChunkPacket.setCachingEnabled( false );
            levelChunkPacket.setRequestSubChunks( false );
            levelChunkPacket.setSubChunksLength( this.getAvailableSubChunks() );
            this.writeTo( byteBuf );
            levelChunkPacket.setData( byteBuf.array() );
            return levelChunkPacket;
        } finally {
            byteBuf.release();
        }
    }

    public long toChunkHash() {
        return Utils.toLong( this.chunkX, this.chunkZ );
    }
}
