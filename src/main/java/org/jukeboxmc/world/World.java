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
import org.jukeboxmc.util.Pair;
import org.jukeboxmc.util.PerformanceCheck;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.ChunkLoader;
import org.jukeboxmc.world.chunk.ChunkManager;
import org.jukeboxmc.world.gamerule.GameRule;
import org.jukeboxmc.world.gamerule.GameRules;
import org.jukeboxmc.world.generator.Generator;
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

    private final GameRules gameRules;
    private final BlockUpdateList blockUpdateList;

    private Location spawnLocation;
    private Difficulty difficulty;
    private int worldTime;
    private long seed = -1;

    private boolean autoSave = true;
    private boolean showAutoSaveMessage = true;
    private long autoSaveTicker = 0;
    private long autoSaveTick = TimeUnit.MINUTES.toMillis( 5 ) / 50;
    private final AtomicBoolean autoSaving = new AtomicBoolean( false );

    private final Map<Dimension, ChunkManager> chunkManagers;

    private final Map<Dimension, ThreadLocal<Generator>> generators;
    private final Map<Pair<Dimension, Long>, Set<ChunkLoader>> chunkLoaders;
    private final Map<Long, Entity> entities;

    private final Queue<BlockUpdateNormal> blockUpdateNormals = new ConcurrentLinkedQueue<>();

    public World( String name, Server server, Map<Dimension, String> generatorMap ) {
        this.name = name;
        this.server = server;

        this.worldFolder = new File( "./worlds/" + name );
        this.worldFile = new File( this.worldFolder, "level.dat" );
        if ( !this.worldFolder.exists() ) {
            this.worldFolder.mkdirs();
        }

        this.gameRules = new GameRules();

        this.generators = new EnumMap<>( Dimension.class );
        for ( Dimension dimension : Dimension.values() ) {
            String generatorName = generatorMap.get( dimension );
            if ( generatorName == null ) {
                generatorName = server.getDefaultGenerator( dimension );
            }

            final String finalGeneratorName = generatorName;
            this.generators.put( dimension, ThreadLocal.withInitial( () -> server.createGenerator( this, finalGeneratorName, dimension ) ) );
        }

        this.blockUpdateList = new BlockUpdateList();
        this.entities = new ConcurrentHashMap<>();

        this.chunkManagers = new EnumMap<>( Dimension.class );
        for ( Dimension dimension : Dimension.values() ) {
            this.chunkManagers.put( dimension, new ChunkManager( this, dimension ) );
        }

        this.chunkLoaders = new HashMap<>();

        if ( !this.loadLevelFile() ) {
            this.saveLevelDatFile();
        }
    }

    public synchronized void update( long currentTick ) {
        for ( ChunkManager chunkManager : this.chunkManagers.values() ) {
            chunkManager.update();
        }

        if ( this.gameRules.get( GameRule.DO_DAYLIGHT_CYCLE ) ) {
            this.worldTime++;
            while ( this.worldTime >= 24000 ) {
                this.worldTime -= 24000;
            }

            SetTimePacket setTimePacket = new SetTimePacket();
            setTimePacket.setTime( this.worldTime );
            this.sendWorldPacket( setTimePacket );
        }

        if ( this.autoSave && !this.autoSaving.get() && ++this.autoSaveTicker >= this.autoSaveTick ) {
            this.autoSaveTicker = 0;
            this.autoSaving.set( true );
            try {
                this.save(false);
                if ( this.showAutoSaveMessage ) {
                    Server.getInstance().getLogger().info( "The world \"" + this.name + "\" was saved successfully" );
                }
                this.autoSaving.set( false );
            } catch ( Throwable e ) {
                e.printStackTrace();
            }
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
                this.seed = nbt.getLong( "RandomSeed", new Random().nextLong() );
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
            this.spawnLocation = this.getGenerator( Dimension.OVERWORLD ).getSpawnLocation() != null ? new Location( this, this.getGenerator( Dimension.OVERWORLD ).getSpawnLocation() ) : new Location( this, 0, 64, 0 );
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
            this.seed = new Random().nextLong();
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

    public void setDifficulty( Difficulty difficulty ) {
        this.difficulty = difficulty;

        SetDifficultyPacket setDifficultyPacket = new SetDifficultyPacket();
        setDifficultyPacket.setDifficulty( difficulty.ordinal() );
        this.sendWorldPacket( setDifficultyPacket );
    }

    public int getWorldTime() {
        return this.worldTime;
    }

    public void setWorldTime( int worldTime ) {
        this.worldTime = worldTime;

        SetTimePacket setTimePacket = new SetTimePacket();
        setTimePacket.setTime( worldTime );
        this.sendWorldPacket( setTimePacket );
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
            this.db = Iq80DBFactory.factory.open( new File( this.worldFolder, "db" ), new Options().blockSize( 64 * 1024 ).createIfMissing( true ) );
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

    public synchronized Generator getGenerator( Dimension dimension ) {
        return this.generators.get( dimension ).get();
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

    public ChunkManager getChunkManager( Dimension dimension ) {
        return this.chunkManagers.get( dimension );
    }

    public synchronized Collection<Chunk> getChunks( Dimension dimension ) {
        return this.chunkManagers.get( dimension ).getLoadedChunks();
    }

    public synchronized Chunk getChunk( int x, int z, Dimension dimension ) {
        return this.chunkManagers.get( dimension ).getChunk( x, z );
    }

    public synchronized Chunk getLoadedChunk( int x, int z, Dimension dimension ) {
        return this.chunkManagers.get( dimension ).getLoadedChunk( x, z );
    }

    public synchronized CompletableFuture<Chunk> getChunkFuture( int x, int z, Dimension dimension ) {
        return this.chunkManagers.get( dimension ).getChunkFuture( x, z );
    }

    public synchronized CompletableFuture<Chunk> getChunkFuture( int x, int z, Dimension dimension, boolean load, boolean generate, boolean populate ) {
        return this.chunkManagers.get( dimension ).getChunkFuture( x, z, load, generate, populate );
    }

    public void addChunkLoader( int chunkX, int chunkZ, Dimension dimension, ChunkLoader chunkLoader ) {
        synchronized (this.chunkLoaders) {
            this.chunkLoaders.computeIfAbsent( new Pair<>( dimension, Utils.toLong( chunkX, chunkZ ) ), k -> new HashSet<>() ).add( chunkLoader );
        }
    }

    public void removeChunkLoader( int chunkX, int chunkZ, Dimension dimension, ChunkLoader chunkLoader ) {
        synchronized (this.chunkLoaders) {
            this.chunkLoaders.computeIfAbsent( new Pair<>( dimension, Utils.toLong( chunkX, chunkZ ) ), k -> new HashSet<>() ).remove( chunkLoader );
        }
    }

    public Collection<ChunkLoader> getChunkLoaders( int chunkX, int chunkZ, Dimension dimension ) {
        synchronized (this.chunkLoaders) {
            return this.chunkLoaders.get( new Pair<>( dimension, Utils.toLong( chunkX, chunkZ ) ) );
        }
    }

    public synchronized void clearChunks() {
        for ( ChunkManager chunkManager : this.chunkManagers.values() ) {
            chunkManager.close();
        }
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
        boolean changed = chunk.isChanged();
        chunk.setBlock( location.getBlockX(), location.getBlockY(), location.getBlockZ(), layer, block );
        chunk.setChanged( changed );

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

        if ( breakBlock.onBlockBreak( breakPosition ) && breakBlock.canBreakWithHand() ) {
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
            if ( !itemDrops.isEmpty() ) {
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
                Chunk chunk = this.getLoadedChunk( x, z, dimension );
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

    public void sendBlockUpdate( Player player, int runtimeId, Vector location, int layer ) {
        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setRuntimeId( runtimeId );
        updateBlockPacket.setBlockPosition( location.toVector3i() );
        updateBlockPacket.getFlags().addAll( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        updateBlockPacket.setDataLayer( layer );
        player.sendPacket( updateBlockPacket );
    }

    public void sendBlockUpdate( Set<Player> players, int runtimeId, Vector vector, int layer ) {
        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setRuntimeId( runtimeId );
        updateBlockPacket.setBlockPosition( vector.toVector3i() );
        updateBlockPacket.getFlags().addAll( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        updateBlockPacket.setDataLayer( layer );

        for ( Player player : players ) {
            player.sendPacket( updateBlockPacket );
        }
    }

    public void save( boolean sync ) {
        CompletableFuture<Void> chunksFuture = this.saveChunks();

        if ( sync ) {
            chunksFuture.join();
        }
    }

    public synchronized CompletableFuture<Void> saveChunks() {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for ( Chunk chunk : this.getChunks( Dimension.OVERWORLD ) ) {
            if ( chunk != null ) {
                futures.add( saveChunk( chunk ) );
            }
        }

        return CompletableFuture.allOf( futures.toArray( new CompletableFuture[0] ) );
    }

    public CompletableFuture<Void> saveChunk( Chunk chunk ) {
        if ( !chunk.isChanged() ) {
            return chunk.save( this.db ).exceptionally( throwable -> {
                throwable.printStackTrace();
                return null;
            } );
        }
        return CompletableFuture.completedFuture( null );
    }

    public void save0() {
        for ( Dimension dimension : Dimension.values() ) {
            Collection<Chunk> chunks = this.getChunks( dimension );
            for ( Chunk chunk : chunks ) {
                chunk.save0( this.db, false );
            }
        }
        if ( this.autoSave ) {
            if ( this.showAutoSaveMessage ) {
                Server.getInstance().getLogger().info( "The world \"" + this.name + "\" was saved successfully" );
            }
        } else {
            Server.getInstance().getLogger().info( "The world \"" + this.name + "\" was saved successfully" );
        }
        this.saveLevelDatFile();
    }

    public DB getDb() {
        return this.db;
    }
}
