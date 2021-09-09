package org.jukeboxmc.world.chunk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import lombok.ToString;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NBTOutputStream;
import org.jukeboxmc.nbt.NbtUtils;
import org.jukeboxmc.network.packet.LevelChunkPacket;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.leveldb.LevelDBChunk;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */

@ToString ( exclude = { "subChunks" } )
public class Chunk extends LevelDBChunk {

    public static final int CHUNK_LAYERS = 2;

    public SubChunk[] subChunks;
    private final World world;
    private final int chunkX;
    private final int chunkZ;
    public Dimension dimension;
    public byte chunkVersion = 21;

    private final List<Entity> entities = new CopyOnWriteArrayList<>();

    public Chunk( World world, int chunkX, int chunkZ, Dimension dimension ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.dimension = dimension;
        this.subChunks = new SubChunk[16];
    }

    public int getHeightMap( int x, int z ) {
        return this.height[( z << 4 ) | x] & 0xFF;
    }

    public void setHeightMap( int x, int z, int value ) {
        this.height[( z << 4 ) | x] = (byte) value;
    }

    public int getRuntimeId( int x, int y, int z, int layer ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        return this.subChunks[subY].getRuntimeId( x & 15, y & 15, z & 15, layer );
    }

    public void setBlock( Vector location, int layer, int runtimeId ) {
        int subY = location.getBlockY() >> 4;
        this.checkAndCreateSubChunks( subY );
        this.subChunks[subY].setBlock( location.getBlockX() & 15, location.getBlockY() & 15, location.getBlockZ() & 15, layer, runtimeId );
    }

    public void setBlock( int x, int y, int z, int layer, int runtimeId ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        this.subChunks[subY].setBlock( x & 15, y & 15, z & 15, layer, runtimeId );
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        Block block = this.subChunks[subY].getBlock( x & 15, y & 15, z & 15, layer );
        block.setLocation( new Location( this.world, new Vector( x, y, z ) ) );
        block.setLayer( layer );
        return block;
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        this.subChunks[subY].setBlock( x & 15, y & 15, z & 15, layer, block );
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public void setDimension( Dimension dimension ) {
        this.dimension = dimension;
    }

    public Biome getBiome( int x, int z ) {
        return Biome.findById( this.biomes[( x << 4 ) | z] & 0xFF );
    }

    public void setBiome( int x, int z, Biome biome ) {
        this.biomes[( x << 4 ) | z] = (byte) biome.getId();
    }

    public void checkAndCreateSubChunks( int subY ) {
        for ( int y = 0; y <= subY; y++ ) {
            if ( this.subChunks[y] == null ) {
                this.subChunks[y] = new SubChunk( y );
            }
        }
    }

    public void checkAndCreateSubChunks( int subY, Consumer<Boolean> consumer ) {
        for ( int y = 0; y <= subY; y++ ) {
            if ( this.subChunks[y] == null ) {
                this.subChunks[y] = new SubChunk( y );
            }
        }
        consumer.accept( true );
    }

    public BlockEntity getBlockEntity( int x, int y, int z ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        return this.subChunks[subY].getBlockEntity( x & 15, y & 15, z & 15 );
    }

    public void setBlockEntity( int x, int y, int z, BlockEntity blockEntity ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        this.subChunks[subY].setBlockEntity( x & 15, y & 15, z & 15, blockEntity );
    }

    public void removeBlockEntity( int x, int y, int z ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        this.subChunks[subY].removeBlockEntity( x & 15, y & 15, z & 15 );
    }

    public List<BlockEntity> getBlockEntitys() {
        List<BlockEntity> blockEntities = new ArrayList<>();
        for ( SubChunk subChunk : this.subChunks ) {
            if ( subChunk != null && subChunk.getBlockEntitys() != null ) {
                blockEntities.addAll( subChunk.getBlockEntitys() );
            }
        }
        return blockEntities;
    }

    public void iterateEntities( Consumer<Entity> consumer ) {
        for ( Entity player : this.entities ) {
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
        if ( !this.entities.contains( entity ) ) {
            this.entities.add( entity );
        }
    }

    public void removeEntity( Entity entity ) {
        this.entities.remove( entity );
    }

    public Entity getEntity( long entityId ) {
        return this.entities.stream().filter( entity -> entity.getEntityId() == entityId ).findFirst().orElse( null );
    }

    public Collection<Entity> getEntities() {
        return this.entities;
    }

    //======== Save and Load =========

    //TODO ADD OLD SAVE KEY
    /*
        public void save( DB db ) {
        WriteBatch writeBatch = db.createWriteBatch();

        for ( int subY = 0; subY < this.subChunks.length; subY++ ) {
            if ( this.subChunks[subY] == null ) {
                continue;
            }
            this.saveChunkSlice( subY, writeBatch );
        }

        //ELSE
        byte[] versionKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x2c );
        BinaryStream versionBuffer = new BinaryStream();
        versionBuffer.writeByte( this.chunkVersion );
        writeBatch.put( versionKey, versionBuffer.getBuffer().array() );

        byte[] finalizedKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x36 );
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer( 1 );
        byteBuf.writeByte( this.populated ? 2 : 0 ).writeByte( 0 ).writeByte( 0 ).writeByte( 0 );
        writeBatch.put( finalizedKey, new BinaryStream( byteBuf ).getBuffer().array() );


        BinaryStream blockEntityBuffer = new BinaryStream();
        NBTOutputStream networkWriter = NbtUtils.createWriterLE( new ByteBufOutputStream( blockEntityBuffer.getBuffer() ) );
        for ( BlockEntity blockEntity : this.getBlockEntitys() ) {
            try {
                NbtMap build = blockEntity.toCompound().build();
                networkWriter.writeTag( build );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

        if ( blockEntityBuffer.readableBytes() > 0 ) {
            byte[] blockEntityKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x31 );
            writeBatch.put( blockEntityKey, blockEntityBuffer.getBuffer().array() );
        }

        byte[] biomeKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x2d );
        BinaryStream biomeBuffer = new BinaryStream();

        for ( short height : this.height ) {
            biomeBuffer.writeLShort( height );
        }
        biomeBuffer.writeBytes( this.biomes );
        writeBatch.put( biomeKey, biomeBuffer.getBuffer().array() );

        db.write( writeBatch );
        try {
            writeBatch.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private void saveChunkSlice( int subY, WriteBatch writeBatch ) {
        BinaryStream buffer = new BinaryStream();
        SubChunk subChunk = this.subChunks[subY];

        buffer.writeByte( (byte) 8 );
        buffer.writeByte( (byte) Chunk.CHUNK_LAYERS );

        for ( int layer = 0; layer < Chunk.CHUNK_LAYERS; layer++ ) {
            Integer[] layerBlocks = subChunk.blocks[layer];
            int[] blockIds = new int[4096];

            Map<Integer, Integer> indexList = new LinkedHashMap<>();
            List<Integer> runtimeIds = new ArrayList<>();

            Integer foundIndex = 0;
            int nextIndex = 0;
            int lastRuntimeId = -1;

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
            int amountOfBlocks = (int) FastMath.floor( 32 / numberOfBits );
            Palette palette = new Palette( buffer, amountOfBlocks, false );

            byte paletteWord = (byte) ( (byte) ( palette.getPaletteVersion().getVersionId() << 1 ) | 1 );
            buffer.writeByte( paletteWord );
            palette.addIndexIDs( blockIds );
            palette.finish();

            buffer.writeLInt( indexList.size() );

            for ( int runtimeId : runtimeIds ) {
                try {
                    NBTOutputStream networkWriter = NbtUtils.createWriterLE( new ByteBufOutputStream( buffer.getBuffer() ) );
                    networkWriter.writeTag( BlockPalette.getBlockNBT( runtimeId ) );
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
        byte[] subChunkKey = Utils.getSubChunkKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x2f, (byte) subY );
        writeBatch.put( subChunkKey, buffer.getBuffer().array() );
    }
     */

    public BinaryStream writeChunk() {
        BinaryStream binaryStream = new BinaryStream();
        for ( SubChunk subChunk : this.subChunks ) {
            if ( subChunk != null ) {
                subChunk.writeTo( binaryStream );
            }
        }
        binaryStream.writeBytes( this.biomes );
        binaryStream.writeUnsignedVarInt( 0 ); //Extradata


        List<BlockEntity> blockEntitys = this.getBlockEntitys();
        if ( !blockEntitys.isEmpty() ) {
            NBTOutputStream writer = NbtUtils.createNetworkWriter( new ByteBufOutputStream( binaryStream.getBuffer() ) );

            for ( BlockEntity blockEntity : blockEntitys ) {
                try {
                    writer.writeTag( blockEntity.toCompound().build() );
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
        return binaryStream;
    }

    public int getAvailableSubChunks() {
        return Arrays.stream( this.subChunks ).mapToInt( o -> o == null ? 0 : 1 ).sum();
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
