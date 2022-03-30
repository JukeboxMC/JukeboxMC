package org.jukeboxmc.world;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.PooledByteBufAllocator;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.util.FastMath;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.ReadOptions;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.iq80.leveldb.impl.SnapshotImpl;
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
import org.jukeboxmc.nbt.*;
import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.ChunkCache;
import org.jukeboxmc.world.chunk.ChunkLoader;
import org.jukeboxmc.world.generator.WorldGenerator;
import org.jukeboxmc.world.leveldb.LevelDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private final BlockUpdateList blockUpdateList;

    private Location spawnLocation;
    private Difficulty difficulty;
    private int worldTime;
    private long seed = -1;

    private boolean autoSave = true;
    private boolean showAutoSaveMessage = true;
    private long autoSaveTicker = 0;
    private long autoSaveTick = TimeUnit.MINUTES.toMillis( 5 ) / 50;
    private AtomicBoolean autoSaving = new AtomicBoolean( false );

    private ChunkCache chunkCache;

    private final Long2ObjectMap<Set<ChunkLoader>> chunkLoaders = new Long2ObjectOpenHashMap<>();
    private final Queue<BlockUpdateNormal> blockUpdateNormals = new ConcurrentLinkedQueue<>();
    private final Object2ObjectMap<GameRule<?>, Object> gamerules = new Object2ObjectOpenHashMap<>();
    private final Map<Long, Entity> entities = new ConcurrentHashMap<>();

    private final ExecutorService chunkThread = Executors.newSingleThreadExecutor();
    private final ThreadLocal<WorldGenerator> generatorThreadLocal;

    public World( String name, Server server ) {
        this.worldFolder = new File( "./worlds/" + name );
        this.worldFile = new File( this.worldFolder, "level.dat" );

        if ( !this.worldFolder.exists() ) {
            this.worldFolder.mkdirs();
        }

        this.name = name;
        this.server = server;
        this.generatorThreadLocal = ThreadLocal.withInitial( Server::createWorldGenerator );
        this.blockUpdateList = new BlockUpdateList();
        this.initGameRules();
        if ( !this.loadLevelFile() ) {
            this.saveLevelDatFile();
        }
    }

    public void update( long currentTick ) {
        if ( this.getGameRule( GameRule.DO_DAYLIGHT_CYCLE ) ) {
            this.worldTime++;
            while ( this.worldTime >= 24000 ) {
                this.worldTime -= 24000;
            }

            SetTimePacket setTimePacket = new SetTimePacket();
            setTimePacket.setWorldTime( this.worldTime );
            this.sendWorldPacket( setTimePacket );
        }

        if ( this.autoSave && !this.autoSaving.get() && ++this.autoSaveTicker >= this.autoSaveTick ) {
            this.autoSaveTicker = 0;
            this.autoSaving.set( true );
            this.server.getScheduler().executeAsync( () -> {
                try {
                    this.save();
                    this.autoSaving.set( false );
                } catch ( Throwable e ) {
                    e.printStackTrace();
                }
            } );
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

    public boolean open() {
        try {
            this.db = Iq80DBFactory.factory.open( new File( this.worldFolder, "db" ), new Options().createIfMissing( true ) );
            this.chunkCache = new ChunkCache( this.db );
            return true;
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return false;
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
            this.spawnLocation = this.getWorldGenerator().getSpawnLocation() != null ? new Location( this, this.getWorldGenerator().getSpawnLocation() ) : new Location( this, 0, 64, 0 );
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

        for ( Map.Entry<GameRule<?>, Object> entry : this.gamerules.entrySet() ) {
            compound.put( entry.getKey().getName(), entry.getKey().toCompoundValue( entry.getValue() ) );
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
    private CompletableFuture<Boolean> loadChunkAsync( Chunk chunk ) {
        return CompletableFuture.supplyAsync( () -> {
            try ( SnapshotImpl snapshot = (SnapshotImpl) this.db.getSnapshot() ) {
                ReadOptions readOptions = new ReadOptions().snapshot( snapshot );

                byte[] version = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x2C ), readOptions );
                if ( version == null ) {
                    version = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x76 ), readOptions );
                }

                if ( version == null ) {
                    return false;
                }

                byte[] finalized = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x36 ), readOptions );

                chunk.chunkVersion = version[0];
                chunk.setGenerated( true );
                chunk.setPopulated( true );

                for ( int sectionY = chunk.getMinY() >> 4; sectionY < chunk.getMaxY() >> 4; sectionY++ ) {
                    byte[] chunkData = this.db.get( Utils.getSubChunkKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x2f, (byte) sectionY ), readOptions );

                    if ( chunkData != null ) {
                        LevelDB.loadSection( chunk.getSubChunk( sectionY << 4 ), chunkData );
                    }
                }

                byte[] blockEntities = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x31 ), readOptions );
                if ( blockEntities != null ) {
                    LevelDB.loadBlockEntities( chunk, blockEntities );
                }

                byte[] heightAndBiomes = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x2b ), readOptions );
                if ( heightAndBiomes != null ) {
                    LevelDB.loadHeightAndBiomes( chunk, heightAndBiomes );
                }

                return true;
            } catch ( Throwable throwable ) {
                throwable.printStackTrace();
                return false;
            }
        } );
    }

    private boolean loadChunk( Chunk chunk ) {
        try ( SnapshotImpl snapshot = (SnapshotImpl) this.db.getSnapshot() ) {
            ReadOptions readOptions = new ReadOptions().snapshot( snapshot );

            byte[] version = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x2C ), readOptions );
            if ( version == null ) {
                version = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x76 ), readOptions );
            }

            if ( version == null ) {
                return false;
            }

            byte[] finalized = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x36 ), readOptions );

            chunk.chunkVersion = version[0];
            chunk.setPopulated( finalized == null || finalized[0] == 2 );
            chunk.setGenerated( true );

            for ( int sectionY = chunk.getMinY() >> 4; sectionY < chunk.getMaxY() >> 4; sectionY++ ) {
                byte[] chunkData = this.db.get( Utils.getSubChunkKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x2f, (byte) sectionY ), readOptions );

                if ( chunkData != null ) {
                    LevelDB.loadSection( chunk.getSubChunk( sectionY << 4 ), chunkData );
                }
            }

            byte[] blockEntities = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x31 ), readOptions );
            if ( blockEntities != null ) {
                LevelDB.loadBlockEntities( chunk, blockEntities );
            }

            byte[] heightAndBiomes = this.db.get( Utils.getKey( chunk.getChunkX(), chunk.getChunkZ(), chunk.getDimension(), (byte) 0x2b ), readOptions );
            if ( heightAndBiomes != null ) {
                LevelDB.loadHeightAndBiomes( chunk, heightAndBiomes );
            }

            return true;
        } catch ( Throwable throwable ) {
            throwable.printStackTrace();
            return false;
        }
    }

    public void addLoadChunkTask( Chunk chunk, boolean direct, BooleanConsumer consumer ) {
        if ( direct ) {
            boolean result = this.loadChunk( chunk );

            consumer.accept( result );

            final Set<ChunkLoader> chunkLoaders = this.chunkLoaders.get( chunk.toChunkHash() );

            if ( chunkLoaders != null ) {
                for ( ChunkLoader loader : chunkLoaders ) {
                    loader.chunkLoadCallback( chunk, result );
                }
            }
            return;
        }
        this.server.getScheduler().executeAsync( () -> {
            final boolean result = this.loadChunk( chunk );

            consumer.accept( result );

            final Set<ChunkLoader> chunkLoaders = this.chunkLoaders.get( chunk.toChunkHash() );

            if ( chunkLoaders != null ) {
                for ( ChunkLoader loader : chunkLoaders ) {
                    loader.chunkLoadCallback( chunk, result );
                }
            }
        } );
    }

    public void addGenerationTask( Chunk chunk, Chunk[] chunks ) {
        this.server.getScheduler().executeAsync( () -> {
            final WorldGenerator generator = this.generatorThreadLocal.get();

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
        final long key = Utils.toLong( x, z );

        Chunk chunk = this.chunkCache.getChunk( x, z, Dimension.OVERWORLD );
        if ( chunk != null || !load ) {
            if ( chunk != null && !chunk.isPopulated() && generate ) {
                this.generateChunk( chunk );
            }

            return chunk;
        }

        chunk = new Chunk( this, x, z, Dimension.OVERWORLD );
        this.chunkCache.putChunk( chunk, Dimension.OVERWORLD );

        final Chunk fChunk = chunk;
        this.addLoadChunkTask( chunk, direct, success -> {
            if ( !success && generate ) this.generateChunk( fChunk );
        } );

        return chunk;
    }

    private void generateChunk( Chunk chunk ) {
        final int x = chunk.getChunkX();
        final int z = chunk.getChunkZ();
        final Chunk[] chunks = new Chunk[9];

        int index = 0;
        for ( int nX = -1; nX <= 1; nX++ ) {
            for ( int nZ = -1; nZ <= 1; nZ++ ) {
                final Chunk nChunk;
                if ( nX != 0 || nZ != 0 )
                    nChunk = this.getChunk( x + nX, z + nZ, true, false, true, Dimension.OVERWORLD );
                else nChunk = chunk;

                chunks[index++] = nChunk;
            }
        }

        this.addGenerationTask( chunk, chunks );
    }

    public synchronized void addChunkLoader( int chunkX, int chunkZ, ChunkLoader chunkLoader ) {
        this.chunkLoaders.computeIfAbsent( Utils.toLong( chunkX, chunkZ ), key -> new HashSet<>() ).add( chunkLoader );
    }

    public synchronized void removeChunkLoader( int chunkX, int chunkZ, ChunkLoader chunkLoader ) {
        this.chunkLoaders.computeIfAbsent( Utils.toLong( chunkX, chunkZ ), key -> new HashSet<>() ).remove( chunkLoader );
    }

    public boolean isChunkLoaded( int chunkX, int chunkZ, Dimension dimension ) {
        return this.chunkCache.isChunkLoaded( chunkX, chunkZ, dimension );
    }

    public void clearChunks() {
        this.chunkCache.clearChunks();
    }

    public Collection<Chunk> getChunks( Dimension dimension ) {
        return this.chunkCache.getChunks( dimension );
    }

    public void prepareSpawnRegion() {
        int spawnRadius = 8;

        int chunkX = this.spawnLocation.getChunkX();
        int chunkZ = this.spawnLocation.getChunkZ();

        for ( int x = chunkX - spawnRadius; x <= chunkX + spawnRadius; x++ ) {
            for ( int z = chunkZ - spawnRadius; z <= chunkZ + spawnRadius; z++ ) {
                this.getChunk( x, z, true, true, true, Dimension.OVERWORLD );
            }
        }
    }

    public void save() {
        this.chunkCache.saveAll();
        this.saveLevelDatFile();

        if ( this.autoSave ) {
            if ( this.showAutoSaveMessage ) {
                Server.getInstance().getLogger().info( "The world \"" + this.name + "\" was saved successfully" );
            }
        } else {
            Server.getInstance().getLogger().info( "The world \"" + this.name + "\" was saved successfully" );
        }
    }

    public void close() {
        try {
            this.save();
        } finally {
            try {
                this.db.close();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

    }

    public String getName() {
        return this.name;
    }

    public Server getServer() {
        return this.server;
    }

    public WorldGenerator getWorldGenerator() {
        return this.generatorThreadLocal.get();
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty( Difficulty difficulty ) {
        this.difficulty = difficulty;

        SetDifficultyPacket setDifficultyPacket = new SetDifficultyPacket();
        setDifficultyPacket.setDifficulty( difficulty );
        this.sendWorldPacket( setDifficultyPacket );
    }

    public int getWorldTime() {
        return this.worldTime;
    }

    public void setWorldTime( int worldTime ) {
        this.worldTime = worldTime;

        SetTimePacket setTimePacket = new SetTimePacket();
        setTimePacket.setWorldTime( worldTime );
        this.server.broadcastPacket( setTimePacket );
    }

    public long getSeed() {
        return this.seed;
    }

    public void setSeed( long seed ) {
        this.seed = seed;
    }

    public void setAutoSave( boolean autoSave ) {
        this.autoSave = autoSave;
    }

    public boolean isAutoSave() {
        return this.autoSave;
    }

    public void setShowAutoSaveMessage( boolean showAutoSaveMessage ) {
        this.showAutoSaveMessage = showAutoSaveMessage;
    }

    public boolean isShowAutoSaveMessage() {
        return this.showAutoSaveMessage;
    }

    public void setAutoSaveTick( long autoSaveTick ) {
        this.autoSaveTick = autoSaveTick;
    }

    public void setAutoSaveTick( TimeUnit timeUnit, long value ) {
        this.autoSaveTick = timeUnit.toMillis( value ) / 50;
    }

    public long getAutoSaveTick() {
        return this.autoSaveTick;
    }

    public boolean isAutoSaving() {
        return this.autoSaving.get();
    }

    public void initGameRules() {
        this.setGameRule( GameRule.COMMAND_BLOCK_OUTPUT, true );
        this.setGameRule( GameRule.DO_DAYLIGHT_CYCLE, true );
        this.setGameRule( GameRule.DO_ENTITY_DROPS, true );
        this.setGameRule( GameRule.DO_FIRE_TICK, true );
        this.setGameRule( GameRule.DO_IMMEDIATE_RESPAWN, false );
        this.setGameRule( GameRule.DO_MOB_LOOT, true );
        this.setGameRule( GameRule.DO_MOB_SPAWNING, true );
        this.setGameRule( GameRule.DO_TILE_DROPS, true );
        this.setGameRule( GameRule.DO_WEATHER_CYCLE, true );
        this.setGameRule( GameRule.DROWNING_DAMAGE, true );
        this.setGameRule( GameRule.FALL_DAMAGE, true );
        this.setGameRule( GameRule.FIRE_DAMAGE, true );
        this.setGameRule( GameRule.KEEP_INVENTORY, true );
        this.setGameRule( GameRule.MOB_GRIEFING, true );
        this.setGameRule( GameRule.NATURAL_REGENERATION, false );
        this.setGameRule( GameRule.PVP, true );
        this.setGameRule( GameRule.RANDOM_TICK_SPEED, 1 );
        this.setGameRule( GameRule.SEND_COMMAND_FEEDBACK, true );
        this.setGameRule( GameRule.SHOW_COORDINATES, true );
        this.setGameRule( GameRule.TNT_EXPLODES, true );
        this.setGameRule( GameRule.SHOW_DEATH_MESSAGE, true );
        this.setGameRule( GameRule.RESPAWN_BLOCKS_EXPLODE, true );
    }

    public void setGameRule( GameRule<?> gameRule, Object object ) {
        this.gamerules.put( gameRule, object );
    }

    public <T> T getGameRule( GameRule<T> gameRule ) {
        return (T) this.gamerules.get( gameRule );
    }

    public Map<GameRule<?>, Object> getGamerules() {
        return this.gamerules;
    }

    public GameRule<?> getGameRuleByName( String name ) {
        for ( GameRule<?> gameRule : this.gamerules.keySet() ) {
            if ( gameRule.getName().equalsIgnoreCase( name ) ) {
                return gameRule;
            }
        }
        return null;
    }

    public void updateGameRules() {
        GameRulesChangedPacket gameRulesChangedPacket = new GameRulesChangedPacket();
        gameRulesChangedPacket.setGamerules( this.gamerules );
        this.sendWorldPacket( gameRulesChangedPacket );
    }

    public void addEntity( Entity entity ) {
        if ( !this.entities.containsKey( entity.getEntityId() ) ) {
            this.entities.put( entity.getEntityId(), entity );
        }
    }

    public void removeEntity( Entity entity ) {
        this.entities.remove( entity.getEntityId() );
    }

    public Player getPlayers( long entityId ) {
        return this.entities.get( entityId ) instanceof Player ? (Player) this.entities.get( entityId ) : null;
    }

    public Collection<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for ( Entity entity : this.entities.values() ) {
            if ( entity instanceof Player ) {
                players.add( (Player) entity );
            }
        }
        return players;
    }

    public Entity getEntity( long entityId ) {
        return this.entities.get( entityId );
    }

    public Collection<Entity> getEntities() {
        return this.entities.values();
    }

    public void setSpawnLocation( Location spawnLocation ) {
        this.spawnLocation = spawnLocation;

        for ( Player player : this.getPlayers() ) {
            SetSpawnPositionPacket setSpawnPositionPacket = new SetSpawnPositionPacket();
            setSpawnPositionPacket.setSpawnType( SetSpawnPositionPacket.SpawnType.WORLD );
            setSpawnPositionPacket.setPlayerPosition( player.getSpawnLocation() );
            setSpawnPositionPacket.setWorldSpawn( this.spawnLocation );
            player.sendPacket( setSpawnPositionPacket );
        }
    }

    public Location getSpawnLocation( Dimension dimension ) {
        if ( !dimension.equals( Dimension.OVERWORLD ) ) {
            return new Location( this, this.spawnLocation.divide( 8, 1, 8 ) );
        } else {
            return new Location( this, this.spawnLocation );
        }
    }

    //========= Blocks =========

    public Block getBlock( Vector location ) {
        return this.getBlock( location, 0 );
    }

    public Block getBlock( Vector location, int layer ) {
        Chunk chunk = this.getChunk( location.getChunkX(), location.getChunkZ(), location.getDimension() );
        return chunk.getBlock( location.getBlockX(), location.getBlockY(), location.getBlockZ(), layer );
    }

    public Block getBlock( int x, int y, int z ) {
        return this.getBlock( new Vector( x, y, z ), 0 );
    }

    public Block getBlock( int x, int y, int z, Dimension dimension ) {
        return this.getBlock( new Vector( x, y, z, dimension ), 0 );
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        return this.getBlock( new Vector( x, y, z ), layer );
    }

    public Block getBlock( int x, int y, int z, Dimension dimension, int layer ) {
        return this.getBlock( new Vector( x, y, z, dimension ), layer );
    }

    public int getBlockRuntimeId( Vector location ) {
        return this.getBlockRuntimeId( location, 0 );
    }

    public int getBlockRuntimeId( Vector location, int layer ) {
        Chunk chunk = this.getChunk( location.getChunkX(), location.getChunkZ(), location.getDimension() );
        return chunk.getRuntimeId( location.getBlockX(), location.getBlockY(), location.getBlockZ(), layer );
    }

    public int getBlockRuntimeId( int x, int y, int z ) {
        return this.getBlockRuntimeId( new Vector( x, y, z ), 0 );
    }

    public int getBlockRuntimeId( int x, int y, int z, Dimension dimension ) {
        return this.getBlockRuntimeId( new Vector( x, y, z, dimension ), 0 );
    }

    public int getBlockRuntimeId( int x, int y, int z, int layer ) {
        return this.getBlockRuntimeId( new Vector( x, y, z ), layer );
    }

    public int getBlockRuntimeId( int x, int y, int z, Dimension dimension, int layer ) {
        return this.getBlockRuntimeId( new Vector( x, y, z, dimension ), layer );
    }

    public void setBlock( Vector location, Block block ) {
        this.setBlock( location, block, 0, location.getDimension(), true );
    }

    public void setBlock( Vector location, Block block, int layer ) {
        this.setBlock( location, block, layer, location.getDimension(), true );
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
        updateBlockPacket.setPosition( location );
        updateBlockPacket.setBlockId( block.getRuntimeId() );
        updateBlockPacket.setFlags( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        updateBlockPacket.setLayer( layer );
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

        if ( breakBlock.onBlockBreak( breakPosition ) && breakBlock.getToolType().equals( item.getItemToolType() ) || breakBlock.canBreakWithHand() ) {
            BlockEntity blockEntity = this.getBlockEntity( breakPosition, breakPosition.getDimension() );
            if ( blockEntity != null ) {
                this.removeBlockEntity( breakPosition, breakPosition.getDimension() );
            }


            if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
                if ( item instanceof Durability ) {
                    item.updateItem( player, 1 );
                }

                player.exhaust( 0.025f );

                List<EntityItem> itemDrops = new ArrayList<>();
                for ( Item droppedItem : blockBreakEvent.getDrops() ) {
                    if ( !droppedItem.getItemType().equals( ItemType.AIR ) ) {
                        itemDrops.add( player.getWorld().dropItem( droppedItem, breakPosition, null ) );
                    }
                }
                if ( !itemDrops.isEmpty() ) itemDrops.forEach( Entity::spawn );
            }
        }

        blockBreakEvent.getBlock().playBlockBreakSound();
        this.sendLevelEvent( breakPosition, LevelEvent.PARTICLE_DESTROY_BLOCK, breakBlock.getRuntimeId() );
    }

    public void updateBlockAround( Vector vector ) {
        Block block = this.getBlock( vector );
        for ( BlockFace blockFace : BlockFace.values() ) {
            Block blockLayer0 = block.getSide( blockFace, 0 );
            Block blockLayer1 = block.getSide( blockFace, 1 );
            this.blockUpdateNormals.add( new BlockUpdateNormal( blockLayer0, blockFace ) );
            this.blockUpdateNormals.add( new BlockUpdateNormal( blockLayer1, blockFace ) );
        }
    }

    public void scheduleBlockUpdate( Location location, long delay ) {
        this.blockUpdateList.addElement( this.server.getCurrentTick() + delay, location );
    }

    public Vector getSidePosition( Vector blockPosition, BlockFace blockFace ) {
        switch ( blockFace ) {
            case DOWN:
                return this.getRelative( blockPosition, Vector.down() );
            case UP:
                return this.getRelative( blockPosition, Vector.up() );
            case NORTH:
                return this.getRelative( blockPosition, Vector.north() );
            case SOUTH:
                return this.getRelative( blockPosition, Vector.south() );
            case WEST:
                return this.getRelative( blockPosition, Vector.west() );
            case EAST:
                return this.getRelative( blockPosition, Vector.east() );
        }
        return null;
    }

    private Vector getRelative( Vector blockPosition, Vector position ) {
        float x = blockPosition.getX() + position.getX();
        float y = blockPosition.getY() + position.getY();
        float z = blockPosition.getZ() + position.getZ();
        return new Vector( x, y, z, blockPosition.getDimension() );
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
                clickedBlock.getBlockType().equals( BlockType.AIR ) ? PlayerInteractEvent.Action.RIGHT_CLICK_AIR :
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
                this.playSound( placePosition, LevelSound.PLACE, placedBlock.getRuntimeId() );
            }
            return success;
        }
        return true;
    }

    //========= BlockEntities =========

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

    //========= Biome =========

    public Biome getBiome( Vector location, Dimension dimension ) {
        Chunk chunk = this.getChunk( location.getBlockX() >> 4, location.getBlockZ() >> 4, dimension );
        return chunk.getBiome( location.getBlockX() & 15, location.getBlockY() & 15, location.getBlockZ() & 15 );
    }

    public void setBiome( Vector location, Dimension dimension, Biome biome ) {
        Chunk chunk = this.getChunk( location.getBlockX() >> 4, location.getBlockZ() >> 4, dimension );
        chunk.setBiome( location.getBlockX() & 15, location.getBlockY() & 15, location.getBlockZ() & 15, biome );
    }

    //========= Other =========

    public void sendWorldPacket( Packet packet ) {
        for ( Player player : this.getPlayers() ) {
            player.sendPacket( packet );
        }
    }

    public void sendDimensionPacket( Packet packet, Dimension dimension ) {
        for ( Player player : this.getPlayers() ) {
            if ( player.getDimension().equals( dimension ) ) {
                player.sendPacket( packet );
            }
        }
    }

    public void sendChunkPacket( int chunkX, int chunkZ, Packet packet ) {
        for ( Player player : this.getPlayers() ) {
            if ( player != null && player.isChunkLoaded( chunkX, chunkZ ) ) {
                player.sendPacket( packet );
            }
        }
    }

    public void sendBlockUpdate( Block block ) {
        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setBlockId( block.getRuntimeId() );
        updateBlockPacket.setPosition( block.getLocation() );
        updateBlockPacket.setFlags( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        updateBlockPacket.setLayer( block.getLayer() );
        this.sendDimensionPacket( updateBlockPacket, block.getLocation().getDimension() );
    }

    public void sendLevelEvent( Vector position, LevelEvent levelEvent ) {
        this.sendLevelEvent( null, position, levelEvent, 0 );
    }

    public void sendLevelEvent( Player player, Vector position, LevelEvent levelEvent ) {
        this.sendLevelEvent( player, position, levelEvent, 0 );
    }

    public void sendLevelEvent( Vector position, LevelEvent levelEvent, int data ) {
        this.sendLevelEvent( null, position, levelEvent, data );
    }

    public void sendLevelEvent( Player player, Vector position, LevelEvent levelEvent, int data ) {
        LevelEventPacket levelEventPacket = new LevelEventPacket();
        levelEventPacket.setPosition( position );
        levelEventPacket.setLevelEventId( levelEvent.getId() );
        levelEventPacket.setData( data );

        if ( player != null ) {
            player.sendPacket( levelEventPacket );
        } else {
            this.sendChunkPacket( position.getBlockX() >> 4, position.getBlockZ() >> 4, levelEventPacket );
        }
    }

    public void playSound( Location location, LevelSound levelSound ) {
        this.playSound( null, location, levelSound, -1, ":", false, false );
    }

    public void playSound( Vector position, LevelSound levelSound ) {
        this.playSound( null, position, levelSound, -1, ":", false, false );
    }

    public void playSound( Player player, LevelSound levelSound ) {
        this.playSound( player, player.getLocation(), levelSound, -1, ":", false, false );
    }

    public void playSound( Vector position, LevelSound levelSound, int data ) {
        this.playSound( null, position, levelSound, data, ":", false, false );
    }

    public void playSound( Vector position, LevelSound levelSound, int data, String entityIdentifier ) {
        this.playSound( null, position, levelSound, data, entityIdentifier, false, false );
    }

    public void playSound( Vector position, LevelSound levelSound, int data, String entityIdentifier, boolean isBaby, boolean isGlobal ) {
        this.playSound( null, position, levelSound, data, entityIdentifier, isBaby, isGlobal );
    }

    public void playSound( Player player, Vector position, LevelSound levelSound, int data, String entityIdentifier, boolean isBaby, boolean isGlobal ) {
        LevelSoundEventPacket levelSoundEventPacket = new LevelSoundEventPacket();
        levelSoundEventPacket.setLevelSound( levelSound );
        levelSoundEventPacket.setPosition( position );
        levelSoundEventPacket.setExtraData( data );
        levelSoundEventPacket.setEntityIdentifier( entityIdentifier );
        levelSoundEventPacket.setBabyMob( isBaby );
        levelSoundEventPacket.setGlobal( isGlobal );

        if ( player == null ) {
            this.sendChunkPacket( position.getBlockX() >> 4, position.getBlockZ() >> 4, levelSoundEventPacket );
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
        int levelEventId;
        switch ( particle ) {
            case DESTROY_BLOCK:
            case CRACK_BLOCK:
                levelEventId = particle.toLevelEvent().getId();
                break;
            default:
                levelEventId = 0x4000 | particle.toLevelEvent().getId();
        }

        LevelEventPacket levelEventPacket = new LevelEventPacket();
        levelEventPacket.setLevelEventId( levelEventId );
        levelEventPacket.setData( data );
        levelEventPacket.setPosition( position );

        if ( player != null ) {
            player.sendPacket( levelEventPacket );
        } else {
            this.sendChunkPacket( position.getChunkX(), position.getChunkZ(), levelEventPacket );
        }
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
}