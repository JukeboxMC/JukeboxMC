package org.jukeboxmc.world.chunk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.ToString;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.WriteBatch;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAir;
import org.jukeboxmc.block.BlockPalette;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NBTOutputStream;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtUtils;
import org.jukeboxmc.network.packet.LevelChunkPacket;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.Palette;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.leveldb.LevelDBChunk;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */

@ToString(exclude = {"subChunks"})
public class Chunk extends LevelDBChunk {

    public static final int CHUNK_LAYERS = 2;
    private static final BlockAir BLOCK_AIR = new BlockAir();

    private SubChunk[] subChunks;
    private final World world;
    private final int chunkX;
    private final int chunkZ;
    public Dimension dimension;
    public byte chunkVersion = 21;

    private final Map<Long, Entity> entities = new HashMap<>();

    public Chunk( World world, int chunkX, int chunkZ, Dimension dimension ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.dimension = dimension;
        this.subChunks = new SubChunk[24];
    }

    public int getHeightMap( int x, int z ) {
        return this.height[( z << 4 ) | x] & 0xFF;
    }

    public void setHeightMap( int x, int z, int value ) {
        this.height[( z << 4 ) | x] = (byte) value;
    }

    public int getRuntimeId( int x, int y, int z, int layer ) {
        if ( y < -64 || y > 319 ) {
            return BLOCK_AIR.getRuntimeId();
        }
        return this.getSubChunk( y ).getRuntimeId( x & 15, y & 15, z & 15, layer );
    }

    public void setBlock( Vector location, int layer, int runtimeId ) {
        if ( location.getY() < -64 || location.getY() > 319 ) {
            return;
        }
        this.getSubChunk( location.getBlockY() ).setBlock( location.getBlockX() & 15, location.getBlockY() & 15, location.getBlockZ() & 15, layer, runtimeId );
    }

    public void setBlock( int x, int y, int z, int layer, int runtimeId ) {
        if ( y < -64 || y > 319 ) {
            return;
        }
        this.getSubChunk( y ).setBlock( x & 15, y & 15, z & 15, layer, runtimeId );
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        if ( y < -64 || y > 319 ) {
            return BLOCK_AIR;
        }
        Block block = this.getSubChunk( y ).getBlock( x & 15, y & 15, z & 15, layer );
        block.setLocation( new Location( this.world, new Vector( x, y, z ) ) );
        block.setLayer( layer );
        return block;
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        if ( y < -64 || y > 319 ) {
            return;
        }
        this.getSubChunk( y ).setBlock( x & 15, y & 15, z & 15, layer, block );
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public void setDimension( Dimension dimension ) {
        this.dimension = dimension;
    }

    public Biome getBiome( int x, int y, int z ) {
        if ( y < -64 || y > 319 ) {
            return Biome.PLAINS;
        }
        return Biome.findById( this.biomes[this.getSubY( y )].get( Utils.getIndex( x & 15, y & 15, z & 15 ) ) );
    }

    public void setBiome( int x, int y, int z, Biome biome ) {
        if ( y < -64 || y > 319 ) {
            return;
        }
        this.biomes[this.getSubY( y )].set( Utils.getIndex( x, y, z ), biome.getId() );
    }

    public SubChunk getSubChunk( int y ) {
        if ( y < -64 || y > 319 ) {
            return null;
        }

        int subY = this.getSubY( y );
        for ( int y0 = 0; y0 <= subY; y0++ ) {
            if ( this.subChunks[y0] == null ) {
                this.subChunks[y0] = new SubChunk( y0 );
            }
        }

        return this.subChunks[subY];
    }

    public int getSubY( int y ) {
        if ( y < -64 || y > 319 ) {
            return -1;
        }

        return ( y >> 4 ) + 4;
    }

    public BlockEntity getBlockEntity( int x, int y, int z ) {
        if ( y < -64 || y > 319 ) {
            return null;
        }
        return this.getSubChunk( y ).getBlockEntity( x & 15, y & 15, z & 15 );
    }

    public void setBlockEntity( int x, int y, int z, BlockEntity blockEntity ) {
        if ( y < -64 || y > 319 ) {
            return;
        }
        this.getSubChunk( y ).setBlockEntity( x & 15, y & 15, z & 15, blockEntity );
    }

    public void removeBlockEntity( int x, int y, int z ) {
        if ( y < -64 || y > 319 ) {
            return;
        }
        this.getSubChunk( y ).removeBlockEntity( x & 15, y & 15, z & 15 );
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

    public void iterateEntities( Consumer<Entity> consumer ) {
        for ( Entity player : this.entities.values() ) {
            consumer.accept( player );
        }
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }

    public SubChunk[] getSubChunks() {
        return this.subChunks;
    }

    public void addEntity( Entity entity ) {
        if ( !this.entities.containsKey( entity.getEntityId() ) ) {
            this.entities.put( entity.getEntityId(), entity );
        }
    }

    public void removeEntity( Entity entity ) {
        this.entities.remove( entity.getEntityId() );
    }

    public Entity getEntity( long entityId ) {
        Optional<Map.Entry<Long, Entity>> optional = this.entities.entrySet().stream().filter( longEntityEntry -> longEntityEntry.getKey() == entityId ).findFirst();
        return optional.map( Map.Entry::getValue ).orElse( null );
    }

    public Collection<Entity> getEntities() {
        return this.entities.values();
    }

    //======== Save and Load =========

    public void save( DB db ) {
        WriteBatch writeBatch = db.createWriteBatch();

        for ( int subY = 0; subY < this.subChunks.length; subY++ ) {
            if ( this.subChunks[subY] == null ) {
                continue;
            }
            this.saveChunkSlice( subY - 4, writeBatch );
        }

        byte[] versionKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x2c );
        BinaryStream versionBuffer = new BinaryStream();
        versionBuffer.writeByte( this.chunkVersion );
        writeBatch.put( versionKey, versionBuffer.array() );

        byte[] finalizedKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x36 );
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer( 1 );
        byteBuf.writeByte( this.populated ? 2 : 0 ).writeByte( 0 ).writeByte( 0 ).writeByte( 0 );
        writeBatch.put( finalizedKey, new BinaryStream( byteBuf ).array() );


        BinaryStream blockEntityBuffer = new BinaryStream();
        NBTOutputStream networkWriter = NbtUtils.createWriterLE( new ByteBufOutputStream( blockEntityBuffer.getBuffer() ) );
        for ( BlockEntity blockEntity : this.getBlockEntities() ) {
            try {
                NbtMap build = blockEntity.toCompound().build();
                networkWriter.writeTag( build );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

        if ( blockEntityBuffer.readableBytes() > 0 ) {
            byte[] blockEntityKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x31 );
            writeBatch.put( blockEntityKey, blockEntityBuffer.array() );
        }

        //TODO: Implement biomes again (find out which new key is used, and which format biomes are saved)
        byte[] heightAndBiomesKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x2d );
        BinaryStream heightAndBiomesBuffer = new BinaryStream();

        for ( short height : this.height ) {
            heightAndBiomesBuffer.writeLShort( height );
        }

        Palette last = null;

        for ( Palette biomePalette : this.biomes ) {
            if ( biomePalette.equals( last ) ) {
                heightAndBiomesBuffer.writeByte( 0xFF );
                continue;
            }

            last = biomePalette;

            if ( biomePalette.isAllEqual() ) {
                heightAndBiomesBuffer.writeByte( 1 );
                heightAndBiomesBuffer.writeLInt( biomePalette.get( 0 ) );
                continue;
            }

            biomePalette.writeTo( heightAndBiomesBuffer, Palette.WriteType.WRITE_DISK );
        }

        writeBatch.put( heightAndBiomesKey, heightAndBiomesBuffer.array() );

        db.write( writeBatch );
        try {
            writeBatch.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private void saveChunkSlice( int subY, WriteBatch writeBatch ) {
        BinaryStream buffer = new BinaryStream();
        SubChunk subChunk = this.subChunks[subY + 4];

        buffer.writeByte( (byte) 9 );
        buffer.writeByte( (byte) Chunk.CHUNK_LAYERS );
        buffer.writeByte( (byte) subY );

        for ( int layer = 0; layer < Chunk.CHUNK_LAYERS; layer++ ) {
            Map<Integer, Integer> runtimeIds = subChunk.blocks[layer].writeTo( buffer, Palette.WriteType.NONE );
            buffer.writeLInt( runtimeIds.size() );

            for ( int runtimeId : runtimeIds.keySet() ) {
                try {
                    NBTOutputStream networkWriter = NbtUtils.createWriterLE( new ByteBufOutputStream( buffer.getBuffer() ) );
                    networkWriter.writeTag( BlockPalette.getBlockNBT( runtimeId ) );
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
        byte[] subChunkKey = Utils.getSubChunkKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x2f, (byte) subY );
        writeBatch.put( subChunkKey, buffer.array() );
    }

    public BinaryStream writeChunk() {
        BinaryStream binaryStream = new BinaryStream();
        for ( SubChunk subChunk : this.subChunks ) {
            if ( subChunk != null ) {
                subChunk.writeTo( binaryStream );
            }
        }

        for ( Palette biome : this.biomes ) {
            biome.writeTo( binaryStream, Palette.WriteType.WRITE_NETWORK );
        }

        binaryStream.writeByte( 0 ); // education edition - border blocks

        List<BlockEntity> blockEntities = this.getBlockEntities();
        if ( !blockEntities.isEmpty() ) {
            NBTOutputStream writer = NbtUtils.createNetworkWriter( new ByteBufOutputStream( binaryStream.getBuffer() ) );

            for ( BlockEntity blockEntity : blockEntities ) {
                try {
                    NbtMap build = blockEntity.toCompound().build();
                    writer.writeTag( build );
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
        return binaryStream;
    }

    public int getAvailableSubChunks() {
        int sum = 0;
        for ( SubChunk o : this.subChunks ) {
            if ( o != null ) {
                sum++;
            }
        }
        return sum;
    }

    public LevelChunkPacket createLevelChunkPacket() {
        ByteBuf buffer = this.writeChunk().getBuffer();
        LevelChunkPacket levelChunkPacket = new LevelChunkPacket();
        levelChunkPacket.setChunkX( this.getChunkX() );
        levelChunkPacket.setChunkZ( this.getChunkZ() );
        levelChunkPacket.setSubChunkCount( this.getAvailableSubChunks() );
        levelChunkPacket.setData( buffer );
        return levelChunkPacket;
    }

    public long toChunkHash() {
        return Utils.toLong( this.chunkX, this.chunkZ );
    }
}
