package org.jukeboxmc.world;

import com.nukkitx.nbt.*;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.GameRuleData;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.packet.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.PooledByteBufAllocator;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.util.FastMath;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAir;
import org.jukeboxmc.block.BlockSlab;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.UpdateReason;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.item.EntityItem;
import org.jukeboxmc.event.block.BlockBreakEvent;
import org.jukeboxmc.event.block.BlockPlaceEvent;
import org.jukeboxmc.event.player.PlayerInteractEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.type.Durability;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.ChunkCache;
import org.jukeboxmc.world.chunk.ChunkLoader;
import org.jukeboxmc.world.gamerule.GameRule;
import org.jukeboxmc.world.gamerule.GameRules;
import org.jukeboxmc.world.generator.Generator;
import org.jukeboxmc.world.leveldb.LevelDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class World {

    private DB db;
    private final File worldFolder;
    private final File worldFile;

    private final String name;
    private final Server server;

    private final GameRules gameRules;
    private final BlockUpdateList blockUpdateList;

    private Location spawnLocation;
    private Difficulty difficulty;
    private int worldTime;
    private long seed;

    private final ChunkCache chunkCache;

    private final ThreadLocal<Generator> generatorThreadLocal;
    private final Long2ObjectMap<Set<ChunkLoader>> chunkLoaders;
    private final Map<Long, Entity> entities;

    private final Queue<BlockUpdateNormal> blockUpdateNormals = new ConcurrentLinkedQueue<>();

    public World( String name, Server server ) {
        this.name = name;
        this.server = server;

        this.worldFolder = new File( "./worlds/" + name );
        this.worldFile = new File( this.worldFolder, "level.dat" );
        if ( !this.worldFolder.exists() ) {
            this.worldFolder.mkdirs();
        }

        this.gameRules = new GameRules();

        this.generatorThreadLocal = ThreadLocal.withInitial( server::createWorldGenerator );
        this.blockUpdateList = new BlockUpdateList();
        this.chunkLoaders = new Long2ObjectOpenHashMap<>();
        this.entities = new ConcurrentHashMap<>();

        this.chunkCache = new ChunkCache();

        if ( !this.loadLevelFile() ) {
            this.saveLevelDatFile();
        }
    }

    public void update( long currentTick ) {
        if ( this.gameRules.get( GameRule.DO_DAYLIGHT_CYCLE ) ) {
            this.worldTime++;
            while ( this.worldTime >= 24000 ) {
                this.worldTime -= 24000;
            }

            SetTimePacket setTimePacket = new SetTimePacket();
            setTimePacket.setTime( this.worldTime );
            this.sendWorldPacket( setTimePacket );
        }

        if ( this.entities.size() > 0 ) {
            for ( Entity entity : this.entities.values() ) {
                if ( entity != null ) {
                    entity.update( currentTick );
                }
            }
        }

        Collection<BlockEntity> blockEntities = this.getBlockEntities( Dimension.OVERWORLD );
        if ( blockEntities.size() > 0 ) {
            for ( BlockEntity blockEntity : blockEntities ) {
                if ( blockEntity != null ) {
                    blockEntity.update( currentTick );
                }
            }
        }

        while ( !this.blockUpdateNormals.isEmpty() ) {
            BlockUpdateNormal updateNormal = this.blockUpdateNormals.poll();
            updateNormal.getBlock().onUpdate( UpdateReason.NORMAL );
        }

        while ( this.blockUpdateList.getNextTaskTime() < currentTick ) {
            Vector blockPosition = this.blockUpdateList.getNextElement();
            if ( blockPosition == null ) {
                break;
            }

            Block block = this.getBlock( blockPosition );
            if ( block != null ) {
                long nextTime = block.onUpdate( UpdateReason.SCHEDULED );

                if ( nextTime > currentTick ) {
                    this.blockUpdateList.addElement( nextTime, blockPosition );
                }
            }
        }
    }

    public boolean loadLevelFile() {
        try ( FileInputStream stream = new FileInputStream( this.worldFile ) ) {
            stream.skip( 8 );
            byte[] data = new byte[stream.available()];
            stream.read( data );

            ByteBuf allocate = Utils.allocate( data );
            try ( NBTInputStream networkReader = NbtUtils.createReaderLE( new ByteBufInputStream( allocate ) ) ) {
                NbtMap nbt = (NbtMap) networkReader.readTag();

                this.spawnLocation = new Location( this, nbt.getInt( "SpawnX", 0 ), nbt.getInt( "SpawnY", 64 ), nbt.getInt( "SpawnZ", 0 ) );
                this.difficulty = Difficulty.getDifficulty( nbt.getInt( "Difficulty", 2 ) );
                this.worldTime = nbt.getInt( "Time", 1000 );
                this.seed = nbt.getLong( "RandomSeed", ThreadLocalRandom.current().nextLong() );
                return true;
            } catch ( IOException ignore ) {
            }
        } catch ( IOException ignore ) {
        }
        return false;
    }

    @SneakyThrows
    private void saveLevelDatFile() {
        File worldFolder = new File( "./worlds/" + this.name );
        File levelDat = new File( worldFolder, "level.dat" );

        NbtMapBuilder compound;
        if ( levelDat.exists() ) {
            FileUtils.copyFile( levelDat, new File( worldFolder, "level.dat_old" ) );

            try ( FileInputStream stream = new FileInputStream( levelDat ) ) {
                stream.skip( 8 );
                byte[] data = new byte[stream.available()];
                stream.read( data );
                ByteBuf allocate = Utils.allocate( data );
                try ( NBTInputStream reader = NbtUtils.createReaderLE( new ByteBufInputStream( allocate ) ) ) {
                    compound = ( (NbtMap) reader.readTag() ).toBuilder();
                }
            }
            levelDat.delete();
        } else {
            compound = NbtMap.builder();
        }

        compound.putInt( "StorageVersion", 8 );

        if ( this.spawnLocation == null ) {
            this.spawnLocation = this.getGenerator().getSpawnLocation() != null ? new Location( this, this.getGenerator().getSpawnLocation() ) : new Location( this, 0, 64, 0 );
        }

        compound.putInt( "SpawnX", this.spawnLocation.getBlockX() );
        compound.putInt( "SpawnY", this.spawnLocation.getBlockY() );
        compound.putInt( "SpawnZ", this.spawnLocation.getBlockZ() );

        if ( this.difficulty == null ) {
            this.difficulty = Difficulty.NORMAL;
        }

        compound.putInt( "Difficulty", this.difficulty.ordinal() );

        compound.putString( "LevelName", this.name );
        compound.putLong( "Time", this.worldTime );

        if ( this.seed == -1 ) {
            this.seed = ThreadLocalRandom.current().nextLong();
        }
        compound.putLong( "RandomSeed", this.seed );


        for ( GameRuleData<?> gameRule : this.gameRules.getGameRules() ) {
            compound.put( gameRule.getName(), gameRule.getValue() );
        }

        try ( FileOutputStream stream = new FileOutputStream( levelDat ) ) {
            stream.write( new byte[8] );

            ByteBuf data = PooledByteBufAllocator.DEFAULT.heapBuffer();
            try ( NBTOutputStream writer = NbtUtils.createWriterLE( new ByteBufOutputStream( data ) ) ) {
                writer.writeTag( compound.build() );
                stream.write( data.array(), data.arrayOffset(), data.readableBytes() );
            } finally {
                data.release();
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public Server getServer() {
        return this.server;
    }

    public GameRules getGameRules() {
        return this.gameRules;
    }

    public Location getSpawnLocation() {
        return this.spawnLocation.add( 0.5f, 0, 0.5f );
    }

    public void setSpawnLocation( Location spawnLocation ) {
        this.spawnLocation = spawnLocation;

        SetSpawnPositionPacket setSpawnPositionPacket = new SetSpawnPositionPacket();
        setSpawnPositionPacket.setSpawnType( SetSpawnPositionPacket.Type.WORLD_SPAWN );
        setSpawnPositionPacket.setBlockPosition( spawnLocation.toVector3i() );
        setSpawnPositionPacket.setDimensionId( Dimension.OVERWORLD.ordinal() );
        this.server.broadcastPacket( setSpawnPositionPacket );
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public int getWorldTime() {
        return this.worldTime;
    }

    public long getSeed() {
        return this.seed;
    }

    public void addEntity( Entity entity ) {
        this.entities.put( entity.getEntityId(), entity );
    }

    public void removeEntity( Entity entity ) {
        this.entities.remove( entity.getEntityId() );
    }

    public Collection<Entity> getEntities() {
        return this.entities.values();
    }

    public Collection<Player> getPlayers() {
        Set<Player> players = new HashSet<>();
        for ( Entity entity : this.entities.values() ) {
            if ( entity instanceof Player player ) {
                players.add( player );
            }
        }
        return players;
    }

    public boolean open() {
        try {
            this.db = Iq80DBFactory.factory.open( new File( this.worldFolder, "db" ), new Options().createIfMissing( true ) );
            return true;
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() {
        try {
            this.db.close();
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    public Generator getGenerator() {
        return this.generatorThreadLocal.get();
    }

    public boolean loadChunk( Chunk chunk ) {
        try {
            byte[] version = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x2C ) );
            if ( version == null ) {
                version = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x76 ) );
            }

            if ( version == null ) {
                return false;
            }

            byte[] finalized = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x36 ) );
            chunk.setPopulated( finalized == null || finalized[0] == 2 );
            chunk.setGenerated( true );

            for ( int sectionY = chunk.getMinY() >> 4; sectionY < chunk.getMaxY() >> 4; sectionY++ ) {
                byte[] chunkData = this.db.get( Utils.getSubChunkKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x2f, (byte) sectionY ) );

                if ( chunkData != null ) {
                    LevelDB.loadSection( chunk.getSubChunk( sectionY << 4 ), chunkData );
                }
            }

            byte[] blockEntities = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x31 ) );
            if ( blockEntities != null ) {
                LevelDB.loadBlockEntities( chunk, blockEntities );
            }

            byte[] heightAndBiomes = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x2b ) );
            if ( heightAndBiomes != null ) {
                LevelDB.loadHeightAndBiomes( chunk, heightAndBiomes );
            }
            return true;
        } catch ( Throwable e ) {
            e.printStackTrace();
            return false;
        }
    }

    public void addLoadChunkTask( Chunk chunk, boolean direct, BooleanConsumer consumer ) {
        if ( direct ) {
            boolean result = this.loadChunk( chunk );
            consumer.accept( result );

            Set<ChunkLoader> chunkLoaders = this.chunkLoaders.get( chunk.toChunkHash() );

            if ( chunkLoaders != null ) {
                for ( ChunkLoader loader : chunkLoaders ) {
                    loader.chunkLoadCallback( chunk, result );
                }
            }
        }

        this.server.getScheduler().executeAsync( () -> {
            boolean result = this.loadChunk( chunk );

            this.server.getScheduler().execute( () -> {
                consumer.accept( result );

                Set<ChunkLoader> chunkLoaders = this.chunkLoaders.get( chunk.toChunkHash() );

                if ( chunkLoaders != null ) {
                    for ( ChunkLoader loader : chunkLoaders ) {
                        loader.chunkLoadCallback( chunk, result );
                    }
                }
            } );
        } );
    }

    public Collection<Chunk> getChunks( Dimension dimension ) {
        return this.chunkCache.getChunks( dimension );
    }

    public Chunk getChunk( int x, int z, Dimension dimension ) {
        return this.getChunk( x, z, true, true, false, dimension );
    }

    public Chunk getChunk( int x, int z, boolean load, Dimension dimension ) {
        return this.getChunk( x, z, load, true, false, dimension );
    }

    public Chunk getChunk( int x, int z, boolean load, boolean generate, Dimension dimension ) {
        return this.getChunk( x, z, load, generate, false, dimension );
    }

    public Chunk getChunk( int x, int z, boolean load, boolean generate, boolean direct, Dimension dimension ) {
        Chunk chunk = this.chunkCache.getChunk( x, z, dimension );
        if ( chunk != null || !load ) {
            if ( chunk != null && !chunk.isPopulated() && generate ) {
                this.generateChunk( chunk, dimension );
            }

            return chunk;
        }

        chunk = new Chunk( this, x, z, dimension );
        this.chunkCache.putChunk( chunk, dimension );

        final Chunk fChunk = chunk;
        this.addLoadChunkTask( chunk, direct, success -> {
            if ( !success && generate ) this.generateChunk( fChunk, dimension );
        } );

        return chunk;
    }

    private void generateChunk( Chunk chunk, Dimension dimension ) {
        final int x = chunk.getChunkX();
        final int z = chunk.getChunkZ();
        final Chunk[] chunks = new Chunk[9];

        int index = 0;
        for ( int nX = -1; nX <= 1; nX++ ) {
            for ( int nZ = -1; nZ <= 1; nZ++ ) {
                final Chunk nChunk;
                if ( nX != 0 || nZ != 0 ) nChunk = this.getChunk( x + nX, z + nZ, true, false, false, dimension );
                else nChunk = chunk;

                chunks[index++] = nChunk;
            }
        }

        this.addGenerationTask( chunk, chunks );
    }

    public void addChunkLoader( int chunkX, int chunkZ, ChunkLoader chunkLoader ) {
        this.chunkLoaders.computeIfAbsent( Utils.toLong( chunkX, chunkZ ), key -> new HashSet<>() ).add( chunkLoader );
    }

    public void removeChunkLoader( int chunkX, int chunkZ, ChunkLoader chunkLoader ) {
        this.chunkLoaders.computeIfAbsent( Utils.toLong( chunkX, chunkZ ), key -> new HashSet<>() ).remove( chunkLoader );
    }

    public void clearChunks() {
        this.chunkCache.clearChunks();
    }

    public void addGenerationTask( Chunk chunk, Chunk[] chunks ) {
        this.server.getScheduler().executeAsync( () -> {
            Generator generator = this.generatorThreadLocal.get();

            try {
                generator.init( this, chunk, chunks );

                for ( Chunk nChunk : chunks ) {
                    if ( !nChunk.isGenerated() ) {
                        try {
                            generator.generate( nChunk.getChunkX(), nChunk.getChunkZ() );
                        } finally {
                            nChunk.setGenerated( true );
                        }
                    }
                }

                try {
                    generator.populate( chunk.getChunkX(), chunk.getChunkZ() );
                } finally {
                    chunk.setPopulated( true );
                }
            } finally {
                generator.clear();
            }

            this.server.getScheduler().execute( () -> {
                final Set<ChunkLoader> chunkLoaders = this.chunkLoaders.get( chunk.toChunkHash() );

                if ( chunkLoaders != null ) {
                    for ( ChunkLoader loader : chunkLoaders ) {
                        loader.chunkGenerationCallback( chunk );
                    }
                }
            } );
        } );
    }

    public int getBlockRuntimeId( int x, int y, int z, int layer, Dimension dimension ) {
        Chunk chunk = this.getChunk( x >> 4, z >> 4, dimension );
        return chunk.getBlock( x, y, z, layer ).getRuntimeId();
    }

    public Block getBlock( int x, int y, int z, int layer, Dimension dimension ) {
        Chunk chunk = this.getChunk( x >> 4, z >> 4, dimension );
        return chunk.getBlock( x, y, z, layer );
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        return this.getBlock( x, y, z, layer, Dimension.OVERWORLD );
    }

    public Block getBlock( Vector vector ) {
        return this.getBlock( vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), 0, vector.getDimension() );
    }

    public Block getBlock( Vector vector, int layer ) {
        return this.getBlock( vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), layer, vector.getDimension() );
    }

    public void setBlock( Vector location, Block block ) {
        this.setBlock( location, block, 0, location.getDimension(), true );
    }

    public void setBlock( int x, int y, int z, Block block, int layer ) {
        this.setBlock( new Vector( x, y, z ), block, layer );
    }

    public void setBlock( Vector vector, Block block, int layer ) {
        this.setBlock( vector, block, layer, Dimension.OVERWORLD, true );
    }

    public void setBlock( Vector location, Block block, int layer, boolean updateBlock ) {
        this.setBlock( location, block, layer, location.getDimension(), updateBlock );
    }

    public void setBlock( Vector location, Block block, int layer, Dimension dimension, boolean updateBlock ) {
        Chunk chunk = this.getChunk( location.getBlockX() >> 4, location.getBlockZ() >> 4, dimension );
        chunk.setBlock( location.getBlockX(), location.getBlockY(), location.getBlockZ(), layer, block );

        Location blockLocation = new Location( this, location );
        blockLocation.setDimension( dimension );
        block.setLocation( blockLocation );
        block.setLayer( layer );

        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setBlockPosition( location.toVector3i() );
        updateBlockPacket.setRuntimeId( block.getRuntimeId() );
        updateBlockPacket.setDataLayer( layer );
        updateBlockPacket.getFlags().addAll( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        this.sendDimensionPacket( updateBlockPacket, location.getDimension() );

        if ( !block.hasBlockEntity() ) {
            chunk.removeBlockEntity( location.getBlockX(), location.getBlockY(), location.getBlockZ() );
        }

        if ( updateBlock ) {
            block.onUpdate( UpdateReason.NORMAL );
            this.getBlock( location, layer == 0 ? 1 : 0 ).onUpdate( UpdateReason.NORMAL );
            this.updateBlockAround( location );

            long next;
            for ( BlockFace blockFace : BlockFace.values() ) {
                Block blockSide = block.getSide( blockFace );
                if ( ( next = blockSide.onUpdate( UpdateReason.NEIGHBORS ) ) > this.server.getCurrentTick() ) {
                    this.scheduleBlockUpdate( blockSide.getLocation(), next );
                }
            }
        }
    }

    public void scheduleBlockUpdate( Location location, long delay ) {
        this.blockUpdateList.addElement( this.server.getCurrentTick() + delay, location );
    }

    public void updateBlockAround( Vector location ) {
        Block block = this.getBlock( location );
        for ( BlockFace blockFace : BlockFace.values() ) {
            Block blockLayer0 = block.getSide( blockFace, 0 );
            Block blockLayer1 = block.getSide( blockFace, 1 );
            this.blockUpdateNormals.add( new BlockUpdateNormal( blockLayer0, blockFace ) );
            this.blockUpdateNormals.add( new BlockUpdateNormal( blockLayer1, blockFace ) );
        }
    }

    public void breakBlock( Player player, Vector breakPosition, Item item ) {
        Block breakBlock = this.getBlock( breakPosition );

        if ( player.getGameMode().equals( GameMode.SPECTATOR ) ) {
            breakBlock.sendBlockUpdate( player );
            return;
        }
        BlockBreakEvent blockBreakEvent = new BlockBreakEvent( player, breakBlock, breakBlock.getDrops( item ) );
        Server.getInstance().getPluginManager().callEvent( blockBreakEvent );

        if ( blockBreakEvent.isCancelled() ) {
            breakBlock.sendBlockUpdate( player );
            return;
        }

        if ( breakBlock.onBlockBreak(breakPosition) && breakBlock.canBreakWithHand() ) {
            BlockEntity blockEntity = this.getBlockEntity( breakPosition, breakPosition.getDimension() );
            if ( blockEntity != null ) {
                this.removeBlockEntity( breakPosition, breakPosition.getDimension() );
            }
        }

        if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
            if ( item instanceof Durability ) {
                item.updateItem( player, 1 );
            }
            player.exhaust( 0.025f );

            List<EntityItem> itemDrops = new ArrayList<>();
            for ( Item droppedItem : blockBreakEvent.getDrops() ) {
                if ( !droppedItem.getType().equals( ItemType.AIR ) ) {
                    itemDrops.add( player.getWorld().dropItem( droppedItem, breakPosition, null ) );
                }
            }
            if ( !itemDrops.isEmpty() ){
                itemDrops.forEach( Entity::spawn );
            }
        }

        Block block = blockBreakEvent.getBlock();
        breakBlock.playBlockBreakSound();
        this.sendLevelEvent( breakPosition, LevelEventType.PARTICLE_DESTROY_BLOCK, block.getRuntimeId() );
    }

    public Biome getBiome( int x, int y, int z ) {
        return this.getBiome( x, y, z, Dimension.OVERWORLD );
    }

    public Biome getBiome( int x, int y, int z, Dimension dimension ) {
        return this.getChunk( x >> 4, z >> 4, dimension ).getBiome( x, y, z );
    }

    public BlockEntity getBlockEntity( Vector location, Dimension dimension ) {
        Chunk chunk = this.getChunk( location.getBlockX() >> 4, location.getBlockZ() >> 4, dimension );
        return chunk.getBlockEntity( location.getBlockX(), location.getBlockY(), location.getBlockZ() );
    }

    public void setBlockEntity( Vector location, BlockEntity blockEntity, Dimension dimension ) {
        Chunk chunk = this.getChunk( location.getBlockX() >> 4, location.getBlockZ() >> 4, dimension );
        chunk.setBlockEntity( location.getBlockX(), location.getBlockY(), location.getBlockZ(), blockEntity );
    }

    public void removeBlockEntity( Vector location, Dimension dimension ) {
        Chunk chunk = this.getChunk( location.getBlockX() >> 4, location.getBlockZ() >> 4, dimension );
        chunk.removeBlockEntity( location.getBlockX(), location.getBlockY(), location.getBlockZ() );
    }

    public Collection<BlockEntity> getBlockEntities( Vector location, Dimension dimension ) {
        Chunk chunk = this.getChunk( location.getBlockX() >> 4, location.getBlockZ() >> 4, dimension );
        return chunk.getBlockEntities();
    }

    public Collection<BlockEntity> getBlockEntities( Dimension dimension ) {
        Set<BlockEntity> blockEntities = new HashSet<>();
        for ( Chunk chunk : this.getChunks( dimension ) ) {
            blockEntities.addAll( chunk.getBlockEntities() );
        }
        return blockEntities;
    }

    public void sendLevelEvent( Vector position, LevelEventType levelEventType ) {
        this.sendLevelEvent( null, position, levelEventType, 0 );
    }

    public void sendLevelEvent( Player player, Vector position, LevelEventType levelEventType ) {
        this.sendLevelEvent( player, position, levelEventType, 0 );
    }

    public void sendLevelEvent( Vector position, LevelEventType levelEventType, int data ) {
        this.sendLevelEvent( null, position, levelEventType, data );
    }

    public void sendLevelEvent( Player player, Vector position, LevelEventType levelEventType, int data ) {
        LevelEventPacket levelEventPacket = new LevelEventPacket();
        levelEventPacket.setPosition( position.toVector3f() );
        levelEventPacket.setType( levelEventType );
        levelEventPacket.setData( data );

        if ( player != null ) {
            player.sendPacket( levelEventPacket );
        } else {
            this.sendChunkPacket( position.getBlockX() >> 4, position.getBlockZ() >> 4, levelEventPacket );
        }
    }

    public void playSound( Location location, SoundEvent soundEvent ) {
        this.playSound( null, location, soundEvent, -1, ":", false, false );
    }

    public void playSound( Vector position, SoundEvent soundEvent ) {
        this.playSound( null, position, soundEvent, -1, ":", false, false );
    }

    public void playSound( Player player, SoundEvent soundEvent ) {
        this.playSound( player, player.getLocation(), soundEvent, -1, ":", false, false );
    }

    public void playSound( Vector position, SoundEvent soundEvent, int data ) {
        this.playSound( null, position, soundEvent, data, ":", false, false );
    }

    public void playSound( Vector position, SoundEvent soundEvent, int data, String entityIdentifier ) {
        this.playSound( null, position, soundEvent, data, entityIdentifier, false, false );
    }

    public void playSound( Vector position, SoundEvent soundEvent, int data, String entityIdentifier, boolean isBaby, boolean isGlobal ) {
        this.playSound( null, position, soundEvent, data, entityIdentifier, isBaby, isGlobal );
    }

    public void playSound( Player player, Vector position, SoundEvent soundEvent, int data, String entityIdentifier, boolean isBaby, boolean isGlobal ) {
        LevelSoundEventPacket levelSoundEventPacket = new LevelSoundEventPacket();
        levelSoundEventPacket.setSound( soundEvent );
        levelSoundEventPacket.setPosition( position.toVector3f() );
        levelSoundEventPacket.setExtraData( data );
        levelSoundEventPacket.setIdentifier( entityIdentifier );
        levelSoundEventPacket.setBabySound( isBaby );
        levelSoundEventPacket.setRelativeVolumeDisabled( isGlobal );

        if ( player == null ) {
            this.sendChunkPacket( position.getChunkX(), position.getChunkZ(), levelSoundEventPacket );
        } else {
            player.sendPacket( levelSoundEventPacket );
        }
    }

    public void spawnParticle( Particle particle, Vector position ) {
        this.spawnParticle( null, particle, position, 0 );
    }

    public void spawnParticle( Particle particle, Vector position, int data ) {
        this.spawnParticle( null, particle, position, data );
    }

    public void spawnParticle( Player player, Particle particle, Vector position ) {
        this.spawnParticle( player, particle, position, 0 );
    }

    public void spawnParticle( Player player, Particle particle, Vector position, int data ) {
        LevelEventPacket levelEventPacket = new LevelEventPacket();
        levelEventPacket.setType( particle.toLevelEvent() );
        levelEventPacket.setData( data );
        levelEventPacket.setPosition( position.toVector3f() );

        if ( player != null ) {
            player.sendPacket( levelEventPacket );
        } else {
            this.sendChunkPacket( position.getChunkX(), position.getChunkZ(), levelEventPacket );
        }
    }

    public Collection<Entity> getNearbyEntities( AxisAlignedBB bb, Dimension dimension, Entity entity ) {
        Set<Entity> targetEntity = new HashSet<>();

        int minX = (int) FastMath.floor( ( bb.getMinX() - 2 ) / 16 );
        int maxX = (int) FastMath.ceil( ( bb.getMaxX() + 2 ) / 16 );
        int minZ = (int) FastMath.floor( ( bb.getMinZ() - 2 ) / 16 );
        int maxZ = (int) FastMath.ceil( ( bb.getMaxZ() + 2 ) / 16 );

        for ( int x = minX; x <= maxX; ++x ) {
            for ( int z = minZ; z <= maxZ; ++z ) {
                Chunk chunk = this.getChunk( x, z, dimension );
                if ( chunk != null ) {
                    for ( Entity iterateEntities : chunk.getEntities() ) {
                        if ( iterateEntities == null ) {
                            continue;
                        }
                        if ( !iterateEntities.equals( entity ) ) {
                            AxisAlignedBB boundingBox = iterateEntities.getBoundingBox();
                            if ( boundingBox.intersectsWith( bb ) ) {
                                if ( entity != null ) {
                                    if ( entity.canCollideWith( iterateEntities ) ) {
                                        targetEntity.add( iterateEntities );
                                    }
                                } else {
                                    targetEntity.add( iterateEntities );
                                }
                            }
                        }
                    }
                }
            }
        }
        return targetEntity;
    }

    private Vector getRelative( Vector blockPosition, Vector position ) {
        float x = blockPosition.getX() + position.getX();
        float y = blockPosition.getY() + position.getY();
        float z = blockPosition.getZ() + position.getZ();
        return new Vector( x, y, z, blockPosition.getDimension() );
    }

    public Vector getSidePosition( Vector blockPosition, BlockFace blockFace ) {
        return switch ( blockFace ) {
            case DOWN -> this.getRelative( blockPosition, Vector.down() );
            case UP -> this.getRelative( blockPosition, Vector.up() );
            case NORTH -> this.getRelative( blockPosition, Vector.north() );
            case SOUTH -> this.getRelative( blockPosition, Vector.south() );
            case WEST -> this.getRelative( blockPosition, Vector.west() );
            case EAST -> this.getRelative( blockPosition, Vector.east() );
        };
    }

    public boolean useItemOn( Player player, Vector blockPosition, Vector placePosition, Vector clickedPosition, BlockFace blockFace ) {
        Block clickedBlock = this.getBlock( blockPosition );

        if ( clickedBlock instanceof BlockAir ) {
            return false;
        }

        Item itemInHand = player.getInventory().getItemInHand();
        Block replacedBlock = this.getBlock( placePosition );
        Block placedBlock = itemInHand.getBlock();

        Location location = new Location( this, placePosition );
        location.setDimension( player.getDimension() );
        placedBlock.setLocation( location );

        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent( player,
                clickedBlock.getType().equals( BlockType.AIR ) ? PlayerInteractEvent.Action.RIGHT_CLICK_AIR :
                        PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK, player.getInventory().getItemInHand(), clickedBlock );

        Server.getInstance().getPluginManager().callEvent( playerInteractEvent );

        boolean interact = false;
        if ( !player.isSneaking() ) {
            if ( !playerInteractEvent.isCancelled() ) {
                interact = clickedBlock.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            }
        }

        boolean itemInteract = itemInHand.interact( player, blockFace, clickedPosition, clickedBlock );


        if ( !interact && itemInHand.useOnBlock( player, clickedBlock, location ) ) {
            return true;
        }

        if ( itemInHand instanceof ItemAir ) {
            return interact;
        }

        if ( ( !interact && !itemInteract ) || player.isSneaking() ) {
            if ( clickedBlock.canBeReplaced( placedBlock ) ) {
                placePosition = blockPosition;
            } else if ( !( replacedBlock.canBeReplaced( placedBlock ) || ( player.getInventory().getItemInHand().getBlock() instanceof BlockSlab && replacedBlock instanceof BlockSlab ) ) ) {
                return false;
            }

            if ( placedBlock.isSolid() ) {
                Collection<Entity> nearbyEntities = this.getNearbyEntities( placedBlock.getBoundingBox(), location.getDimension(), null );
                if ( !nearbyEntities.isEmpty() ) {
                    return false;
                }
                AxisAlignedBB boundingBox = player.getBoundingBox();
                if ( placedBlock.getBoundingBox().intersectsWith( boundingBox ) ) {
                    return false;
                }
            }

            if ( player.getGameMode().equals( GameMode.SPECTATOR ) ) {
                return false;
            }

            BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent( player, placedBlock, replacedBlock, clickedBlock );
            Server.getInstance().getPluginManager().callEvent( blockPlaceEvent );

            if ( blockPlaceEvent.isCancelled() ) {
                return false;
            }
            boolean success = blockPlaceEvent.getPlacedBlock().placeBlock( player, this, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
            if ( success ) {
                if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
                    Item resultItem = itemInHand.setAmount( itemInHand.getAmount() - 1 );
                    if ( itemInHand.getAmount() != 0 ) {
                        player.getInventory().setItemInHand( resultItem );
                    } else {
                        player.getInventory().setItemInHand( new ItemAir() );
                    }
                    player.getInventory().sendContents( player.getInventory().getItemInHandSlot(), player );
                }
                this.playSound( placePosition, SoundEvent.PLACE, placedBlock.getRuntimeId() );
            }
            return success;
        }
        return true;
    }

    public List<AxisAlignedBB> getCollisionCubes( Entity entity, AxisAlignedBB bb, boolean includeEntities ) {
        int minX = (int) FastMath.floor( bb.getMinX() );
        int minY = (int) FastMath.floor( bb.getMinY() );
        int minZ = (int) FastMath.floor( bb.getMinZ() );
        int maxX = (int) FastMath.ceil( bb.getMaxX() );
        int maxY = (int) FastMath.ceil( bb.getMaxY() );
        int maxZ = (int) FastMath.ceil( bb.getMaxZ() );

        List<AxisAlignedBB> collides = new ArrayList<>();

        for ( int z = minZ; z <= maxZ; ++z ) {
            for ( int x = minX; x <= maxX; ++x ) {
                for ( int y = minY; y <= maxY; ++y ) {
                    Block block = this.getBlock( new Vector( x, y, z ), 0 );
                    if ( !block.canPassThrough() && block.getBoundingBox().intersectsWith( bb ) ) {
                        collides.add( block.getBoundingBox() );
                    }
                }
            }
        }

        if ( includeEntities ) {
            for ( Entity nearbyEntity : this.getNearbyEntities( bb.grow( 0.25f, 0.25f, 0.25f ), entity.getDimension(), entity ) ) {
                if ( !nearbyEntity.canPassThrough() ) {
                    collides.add( nearbyEntity.getBoundingBox().clone() );
                }
            }
        }

        return collides;
    }

    public EntityItem dropItem( Item item, Vector location, Vector velocity ) {
        if ( velocity == null ) {
            velocity = new Vector( ThreadLocalRandom.current().nextFloat() * 0.2f - 0.1f,
                    0.2f, ThreadLocalRandom.current().nextFloat() * 0.2f - 0.1f );
        }

        EntityItem entityItem = new EntityItem();
        entityItem.setItem( item );
        entityItem.setVelocity( velocity, false );
        entityItem.setLocation( new Location( this, location ) );
        entityItem.setPickupDelay( 1, TimeUnit.SECONDS );
        return entityItem;
    }

    public Entity getEntity( long entityId ) {
        return this.entities.get( entityId );
    }

    public void sendWorldPacket( BedrockPacket packet ) {
        for ( Player player : this.getPlayers() ) {
            player.sendPacket( packet );
        }
    }

    public void sendDimensionPacket( BedrockPacket packet, Dimension dimension ) {
        for ( Player player : this.getPlayers() ) {
            if ( player.getDimension().equals( dimension ) ) {
                player.sendPacket( packet );
            }
        }
    }

    public void sendChunkPacket( int chunkX, int chunkZ, BedrockPacket packet ) {
        for ( Player player : this.getPlayers() ) {
            if ( player != null ) {
                if ( player.isChunkLoaded( chunkX, chunkZ ) ) {
                    player.sendPacket( packet );
                }
            }
        }
    }

    public void sendBlockUpdate( Block block ) {
        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setRuntimeId( block.getRuntimeId() );
        updateBlockPacket.setBlockPosition( block.getLocation().toVector3i() );
        updateBlockPacket.getFlags().addAll( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        updateBlockPacket.setDataLayer( block.getLayer() );
        this.sendDimensionPacket( updateBlockPacket, block.getLocation().getDimension() );
    }
}
