package org.jukeboxmc.world.chunk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.PooledByteBufAllocator;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lombok.ToString;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.WriteBatch;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAir;
import org.jukeboxmc.block.BlockPalette;
import org.jukeboxmc.block.BlockType;
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
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.palette.object.ObjectPalette;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */

@ToString ( exclude = { "subChunks" } )
public class Chunk {

    public static final int CHUNK_LAYERS = 2;
    private static final BlockAir BLOCK_AIR = new BlockAir();

    private final ObjectPalette<Biome>[] biomes;
    private final short[] height;
    private final SubChunk[] subChunks;
    private final World world;
    private final int chunkX;
    private final int chunkZ;
    public Dimension dimension;
    public byte chunkVersion = 39;
    private boolean populated;
    private boolean generated;

    private final Long2ObjectMap<Entity> entities = new Long2ObjectOpenHashMap<>();

    public Chunk( World world, int chunkX, int chunkZ, Dimension dimension ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.dimension = dimension;

        int fullHeight = Math.abs( this.getMinY() ) + Math.abs( this.getMaxY() ) + 1;

        this.subChunks = new SubChunk[fullHeight >> 4];
        this.height = new short[16 * 16];
        this.biomes = new ObjectPalette[fullHeight >> 4];
        this.populated = false;
        this.generated = false;
    }

    public void setGenerated( boolean generated ) {
        this.generated = generated;
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setPopulated( boolean populated ) {
        this.populated = populated;
    }

    public boolean isPopulated() {
        return populated;
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
        return this.getSubChunk( y ).getRuntimeId( x & 15, ( y - this.getMinY() ) & 15, z & 15, layer );
    }

    public void setBlock( Vector location, int layer, int runtimeId ) {
        if ( location.getY() < -64 || location.getY() > 319 ) {
            return;
        }
        this.getSubChunk( location.getBlockY() ).setBlock( location.getBlockX() & 15, ( location.getBlockY() - this.getMinY() ) & 15, location.getBlockZ() & 15, layer, runtimeId );
    }

    public void setBlock( int x, int y, int z, int layer, int runtimeId ) {
        if ( y < -64 || y > 319 ) {
            return;
        }
        this.getSubChunk( y ).setBlock( x & 15, ( y - this.getMinY() ) & 15, z & 15, layer, runtimeId );
    }

    public void setBlock( int x, int y, int z, int layer, BlockType blockType ) {
        if ( y < -64 || y > 319 ) {
            return;
        }
        this.getSubChunk( y ).setBlock( x & 15, ( y - this.getMinY() ) & 15, z & 15, layer, blockType.getBlock() );
    }

    public void setBlock( int x, int y, int z, BlockType blockType ) {
        if ( y < -64 || y > 319 ) {
            return;
        }
        this.getSubChunk( y ).setBlock( x & 15, ( y - this.getMinY() ) & 15, z & 15, 0, blockType.getBlock() );
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        if ( y < -64 || y > 319 ) {
            return;
        }
        this.getSubChunk( y ).setBlock( x & 15, ( y - this.getMinY() ) & 15, z & 15, layer, block );
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        if ( y < -64 || y > 319 ) {
            return BLOCK_AIR;
        }
        Block block = this.getSubChunk( y ).getBlock( x & 15, ( y - this.getMinY() ) & 15, z & 15, layer );
        block.setLocation( new Location( this.world, new Vector( x, y, z ) ) );
        block.setLayer( layer );
        return block;
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
        return this.getBiomePalette( y ).get( Utils.getIndex( x & 15, y & 15, z & 15 ) );
    }

    public void setBiome( int x, int y, int z, Biome biome ) {
        if ( y < -64 || y > 319 ) {
            return;
        }
        this.getBiomePalette( y ).set( Utils.getIndex( x & 15, y & 15, z & 15 ), biome );
    }

    public ObjectPalette<Biome> getBiomePalette( int y ) {
        if ( y < -64 || y > 319 ) {
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

    public short[] getHeight() {
        return height;
    }

    public SubChunk getSubChunk( int y ) {
        if ( y < -64 || y > 319 ) {
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

    public World getWorld() {
        return this.world;
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

        try ( NBTOutputStream networkWriter = NbtUtils.createWriterLE( new ByteBufOutputStream( blockEntityBuffer.getBuffer() ) ) ) {
            for ( BlockEntity blockEntity : this.getBlockEntities() ) {
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

        if ( blockEntityBuffer.readableBytes() > 0 ) {
            byte[] blockEntityKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x31 );
            writeBatch.put( blockEntityKey, blockEntityBuffer.array() );
        }

        byte[] heightAndBiomesKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x2b );
        BinaryStream heightAndBiomesBuffer = new BinaryStream();

        for ( short height : this.height ) {
            heightAndBiomesBuffer.writeLShort( height );
        }
        ObjectPalette<Biome> last = null;
        for ( ObjectPalette<Biome> biomePalette : this.biomes ) {
            if ( biomePalette == null ) {
                if ( last != null ) {
                    last.writeToStorageRuntime( heightAndBiomesBuffer, Biome::getId, last );
                }
                continue;
            }
            biomePalette.writeToStorageRuntime( heightAndBiomesBuffer, Biome::getId, last );
            last = biomePalette;
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
            subChunk.blocks[layer].writeToStoragePersistent( buffer, BlockPalette::getBlockNBT );
        }

        byte[] subChunkKey = Utils.getSubChunkKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x2f, (byte) subY );
        writeBatch.put( subChunkKey, buffer.array() );
    }

    public BinaryStream writeChunk() {
        BinaryStream buffer = new BinaryStream();
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
            try ( NBTOutputStream writer = NbtUtils.createNetworkWriter( new ByteBufOutputStream( buffer.getBuffer() ) ) ) {
                for ( BlockEntity blockEntity : blockEntities ) {
                    NbtMap tag = blockEntity.toCompound().build();
                    writer.writeTag( blockEntity.toCompound().build() );
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

        return buffer;
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
