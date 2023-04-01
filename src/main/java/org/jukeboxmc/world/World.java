package org.jukeboxmc.world;

import com.google.common.collect.ImmutableSet;
import com.nukkitx.nbt.*;
import com.nukkitx.nbt.util.stream.LittleEndianDataInputStream;
import com.nukkitx.nbt.util.stream.LittleEndianDataOutputStream;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.GameRuleData;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.packet.*;
import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.BlockUpdateList;
import org.jukeboxmc.block.BlockUpdateNormal;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.entity.item.EntityItem;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.ChunkManager;
import org.jukeboxmc.world.gamerule.GameRule;
import org.jukeboxmc.world.gamerule.GameRules;
import org.jukeboxmc.world.generator.Generator;
import org.jukeboxmc.world.leveldb.LevelDB;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class World {

    private final Block BLOCK_AIR = Block.create( BlockType.AIR );
    private final int STORAGE_VERSION = 9;

    private String name;
    private final Server server;
    private final GameRules gameRules;
    private final BlockUpdateList blockUpdateList;

    private final File worldFolder;
    private final File worldFile;
    private final LevelDB levelDB;

    private final Map<Dimension, ChunkManager> chunkManagers;
    private final Map<Dimension, ThreadLocal<Generator>> generators;

    private Difficulty difficulty;
    private Location spawnLocation;
    private long seed;

    private int worldTime;
    private long nextTimeSendTick;

    private final Map<Long, Entity> entities;
    private final Queue<BlockUpdateNormal> blockUpdateNormals;

    public World( String name, Server server, Map<Dimension, String> generatorMap ) {
        this.name = name;
        this.server = server;
        this.gameRules = new GameRules();
        this.blockUpdateList = new BlockUpdateList();

        this.worldFolder = new File( "./worlds/" + name );
        this.worldFile = new File( this.worldFolder, "level.dat" );
        if ( !this.worldFolder.exists() ) {
            this.worldFolder.mkdirs();
        }
        this.levelDB = new LevelDB( this );

        this.chunkManagers = new EnumMap<>( Dimension.class );
        for ( Dimension dimension : Dimension.values() ) {
            this.chunkManagers.put( dimension, new ChunkManager( this, dimension ) );
        }

        AtomicBoolean sendWarning = new AtomicBoolean( false );
        this.generators = new EnumMap<>( Dimension.class );
        for ( Dimension dimension : Dimension.values() ) {
            String generatorName = generatorMap.get( dimension );
            this.generators.put( dimension, ThreadLocal.withInitial( () -> server.createGenerator( generatorName, this, dimension ) ) );
        }

        Generator generator = this.getGenerator( Dimension.OVERWORLD );
        this.difficulty = server.getDifficulty();
        this.spawnLocation = new Location( this, generator.getSpawnLocation() );

        this.entities = new ConcurrentHashMap<>();
        this.blockUpdateNormals = new ConcurrentLinkedQueue<>();

        this.loadLevelFile();
    }

    private void loadLevelFile() {
        if ( this.worldFile.exists() ) {
            try ( LittleEndianDataInputStream inputStream = new LittleEndianDataInputStream( Files.newInputStream( this.worldFile.toPath() ) ); NBTInputStream stream = new NBTInputStream( inputStream ) ) {
                int version = inputStream.readInt();
                int size = inputStream.readInt();

                NbtMap nbtTag = (NbtMap) stream.readTag();
                nbtTag.listenForString( "LevelName", name -> this.name = name );
                nbtTag.listenForInt( "Difficulty", value -> this.difficulty = Difficulty.values()[value] );
                if ( nbtTag.containsKey( "SpawnX" ) && nbtTag.containsKey( "SpawnY" ) && nbtTag.containsKey( "SpawnZ" ) ) {
                    this.spawnLocation = new Location( this, nbtTag.getInt( "SpawnX" ), nbtTag.getInt( "SpawnY" ), nbtTag.getInt( "SpawnZ" ) );
                }
                nbtTag.listenForLong( "RandomSeed", this::setSeed );
                nbtTag.listenForLong( "Time", value -> this.worldTime = (int) value );

                for ( GameRule value : GameRule.values() ) {
                    String identifer = value.getIdentifier().toLowerCase();
                    if ( nbtTag.containsKey( identifer ) ) {
                        this.gameRules.set( value, nbtTag.get( identifer ) );
                    }
                }
            } catch ( IOException e ) {
                throw new RuntimeException( e );
            }
        } else {
            this.seed = ThreadLocalRandom.current().nextInt();
            try {
                if ( this.worldFile.createNewFile() ) {
                    this.saveLevelFile();
                }
            } catch ( IOException e ) {
                throw new RuntimeException( e );
            }
        }
    }

    private void saveLevelFile() {
        NbtMapBuilder nbtTag = NbtMap.builder();
        nbtTag.putInt( "StorageVersion", STORAGE_VERSION );
        nbtTag.putString( "LevelName", this.name );
        nbtTag.putInt( "Difficulty", this.difficulty.ordinal() );
        nbtTag.put( "SpawnX", this.spawnLocation.getBlockX() );
        nbtTag.put( "SpawnY", this.spawnLocation.getBlockY() );
        nbtTag.put( "SpawnZ", this.spawnLocation.getBlockZ() );
        nbtTag.putLong( "RandomSeed", this.seed );
        nbtTag.putLong( "Time", this.worldTime );

        for ( GameRuleData<?> gameRule : this.gameRules.getGameRules() ) {
            if ( gameRule.getValue() instanceof Boolean ) {
                nbtTag.putBoolean( gameRule.getName().toUpperCase(), (boolean) gameRule.getValue() );
            } else if ( gameRule.getValue() instanceof Integer ) {
                nbtTag.putInt( gameRule.getName().toUpperCase(), (int) gameRule.getValue() );
            } else if ( gameRule.getValue() instanceof Float ) {
                nbtTag.putFloat( gameRule.getName().toUpperCase(), (float) gameRule.getValue() );
            }
        }

        byte[] tagBytes;
        try ( ByteArrayOutputStream stream = new ByteArrayOutputStream(); NBTOutputStream nbtOutputStream = NbtUtils.createWriterLE( stream ) ) {
            nbtOutputStream.writeTag( nbtTag.build() );
            tagBytes = stream.toByteArray();
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }

        try ( LittleEndianDataOutputStream stream = new LittleEndianDataOutputStream( Files.newOutputStream( this.worldFile.toPath() ) ) ) {
            stream.writeInt( STORAGE_VERSION );
            stream.writeInt( tagBytes.length );
            stream.write( tagBytes );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    public void update( long currentTick ) {
        if ( currentTick % 100 == 0 ) {
            for ( ChunkManager chunkManager : this.chunkManagers.values() ) {
                chunkManager.tick();
            }
        }

        if ( this.getGameRule( GameRule.DO_DAYLIGHT_CYCLE ) ) {
            this.worldTime += 1;
        }
        if ( currentTick >= this.nextTimeSendTick ) {
            this.setWorldTime( this.worldTime );
            this.nextTimeSendTick = currentTick + 12 * 20; //Client send the time every 12 seconds
        }

        if ( !this.entities.isEmpty() ) {
            for ( Entity entity : this.entities.values() ) {
                entity.update( currentTick );
            }
        }

        for ( Dimension dimension : Dimension.values() ) {
            Collection<BlockEntity> blockEntities = this.getBlockEntities( dimension );
            if ( blockEntities.size() > 0 ) {
                for ( BlockEntity blockEntity : blockEntities ) {
                    if ( blockEntity != null ) {
                        blockEntity.update( currentTick );
                    }
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

    public String getName() {
        return this.name;
    }

    public Server getServer() {
        return this.server;
    }

    public File getWorldFolder() {
        return this.worldFolder;
    }

    public GameRules getGameRules() {
        return this.gameRules;
    }

    public <V> V getGameRule( GameRule gameRule ) {
        return this.gameRules.get( gameRule );
    }

    public void setGameRule( GameRule gameRule, Object value ) {
        this.gameRules.set( gameRule, value );
        for ( Player player : this.getPlayers() ) {
            player.getPlayerConnection().sendPacket( this.gameRules.updatePacket() );
        }
    }

    public synchronized Generator getGenerator( Dimension dimension ) {
        return this.generators.get( dimension ).get();
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

    public Location getSpawnLocation() {
        return this.spawnLocation;
    }

    public void setSpawnLocation( Location spawnLocation ) {
        this.spawnLocation = spawnLocation;

        SetSpawnPositionPacket setSpawnPositionPacket = new SetSpawnPositionPacket();
        setSpawnPositionPacket.setSpawnType( SetSpawnPositionPacket.Type.WORLD_SPAWN );
        setSpawnPositionPacket.setBlockPosition( spawnLocation.toVector3i() );
        setSpawnPositionPacket.setDimensionId( spawnLocation.getDimension().ordinal() );
        this.server.broadcastPacket( setSpawnPositionPacket );
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed( long seed ) {
        this.seed = seed;
    }

    public int getWorldTime() {
        return this.worldTime;
    }

    public void setWorldTime( int worldTime ) {
        this.worldTime = worldTime;

        SetTimePacket setTimePacket = new SetTimePacket();
        setTimePacket.setTime( worldTime );
        Server.getInstance().broadcastPacket( setTimePacket );
    }

    public void addEntity( Entity entity ) {
        this.entities.put( entity.getEntityId(), entity );
    }

    public void removeEntity( Entity entity ) {
        this.entities.remove( entity.getEntityId() );
    }

    public Collection<Entity> getEntitys() {
        return this.entities.values();
    }

    public Collection<Player> getPlayers() {
        return this.entities.values().stream().filter( entity -> entity instanceof Player ).map( entity -> (Player) entity ).collect( Collectors.toSet() );
    }

    public Block getBlock( Vector vector, int layer, Dimension dimension ) {
        Chunk chunk = this.getLoadedChunk( vector.getChunkX(), vector.getChunkZ(), dimension );
        if ( chunk == null ) return BLOCK_AIR;
        return chunk.getBlock( vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), layer );
    }

    public Block getBlock( Vector vector, int layer ) {
        return this.getBlock( vector, layer, Dimension.OVERWORLD );
    }

    public Block getBlock( Vector vector ) {
        return this.getBlock( vector, 0, vector.getDimension() );
    }

    public Block getBlock( Vector vector, Dimension dimension ) {
        return this.getBlock( vector, 0, dimension );
    }

    public Block getBlock( int x, int y, int z, int layer, Dimension dimension ) {
        return this.getBlock( new Vector( x, y, z ), layer, dimension );
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        return this.getBlock( x, y, z, layer, Dimension.OVERWORLD );
    }

    public Block getBlock( int x, int y, int z ) {
        return this.getBlock( x, y, z, 0 );
    }

    public void setBlock( Vector vector, Block block, int layer, Dimension dimension, boolean updateBlock ) {
        Chunk chunk = this.getLoadedChunk( vector.getChunkX(), vector.getChunkZ(), dimension );
        if ( chunk == null ) return;
        chunk.setBlock( vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), layer, block );
        chunk.setDirty( true );

        Location location = new Location( this, vector, dimension );
        block.setLocation( location );
        block.setLayer( layer );

        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setBlockPosition( vector.toVector3i() );
        updateBlockPacket.setRuntimeId( block.getRuntimeId() );
        updateBlockPacket.setDataLayer( layer );
        updateBlockPacket.getFlags().addAll( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        this.sendChunkPacket( vector.getChunkX(), vector.getChunkZ(), updateBlockPacket );

        if ( block.getBlockEntity() != null ) {
            this.removeBlockEntity( vector, dimension );
        }

        if ( updateBlock ) {
            block.onUpdate( UpdateReason.NORMAL );
            this.getBlock( vector, layer == 0 ? 1 : 0 ).onUpdate( UpdateReason.NORMAL );
            this.updateBlockAround( vector );

            long next;
            for ( BlockFace blockFace : BlockFace.values() ) {
                Block blockSide = block.getSide( blockFace );
                if ( ( next = blockSide.onUpdate( UpdateReason.NEIGHBORS ) ) > this.server.getCurrentTick() ) {
                    this.scheduleBlockUpdate( blockSide.getLocation(), next );
                }
            }
        }
    }

    public void setBlock( Vector vector, Block block, int layer, Dimension dimension ) {
        this.setBlock( vector, block, layer, dimension, true );
    }

    public void setBlock( Vector vector, Block block, int layer ) {
        this.setBlock( vector, block, layer, vector.getDimension() );
    }

    public void setBlock( Vector vector, Block block ) {
        this.setBlock( vector, block, 0, vector.getDimension() );
    }

    public void setBlock( int x, int y, int z, int layer, Block block, Dimension dimension ) {
        this.setBlock( new Vector( x, y, z ), block, layer, dimension );
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        this.setBlock( x, y, z, layer, block, Dimension.OVERWORLD );
    }

    public void setBlock( int x, int y, int z, Block block ) {
        this.setBlock( x, y, z, 0, block );
    }

    public synchronized BlockEntity getBlockEntity( Vector vector, Dimension dimension ) {
        Chunk chunk = this.getLoadedChunk( vector.getChunkX(), vector.getChunkZ(), dimension );
        if ( chunk == null ) return null;
        return chunk.getBlockEntity( vector.getBlockX(), vector.getBlockY(), vector.getBlockZ() );
    }

    public synchronized BlockEntity getBlockEntity( int x, int y, int z, Dimension dimension ) {
        Chunk chunk = this.getLoadedChunk( x >> 4, z >> 4, dimension );
        if ( chunk == null ) return null;
        return chunk.getBlockEntity( x, y, z );
    }

    public synchronized void setBlockEntity( int x, int y, int z, BlockEntity blockEntity, Dimension dimension ) {
        Chunk chunk = this.getLoadedChunk( x >> 4, z >> 4, dimension );
        if ( chunk == null ) return;
        chunk.setBlockEntity( x, y, z, blockEntity );
    }

    public synchronized void setBlockEntity( Vector vector, BlockEntity blockEntity, Dimension dimension ) {
        Chunk chunk = this.getLoadedChunk( vector.getChunkX(), vector.getChunkZ(), dimension );
        if ( chunk == null ) return;
        chunk.setBlockEntity( vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), blockEntity );
    }

    public synchronized void removeBlockEntity( Vector vector, Dimension dimension ) {
        Chunk chunk = this.getLoadedChunk( vector.getChunkX(), vector.getChunkZ(), dimension );
        if ( chunk == null ) return;
        chunk.removeBlockEntity( vector.getBlockX(), vector.getBlockY(), vector.getBlockZ() );
    }

    public synchronized void removeBlockEntity( int x, int y, int z, Dimension dimension ) {
        Chunk chunk = this.getLoadedChunk( x >> 4, z >> 4, dimension );
        if ( chunk == null ) return;
        chunk.removeBlockEntity( x, y, z );
    }

    public synchronized Collection<BlockEntity> getBlockEntities( Dimension dimension ) {
        Set<BlockEntity> blockEntities = new LinkedHashSet<>();
        for ( Chunk loadedChunk : this.chunkManagers.get( dimension ).getLoadedChunks() ) {
            blockEntities.addAll( loadedChunk.getBlockEntities() );
        }
        return blockEntities;
    }

    public synchronized Biome getBiome( Vector vector, Dimension dimension ) {
        Chunk chunk = this.getChunk( vector.getChunkX(), vector.getChunkZ(), dimension );
        return chunk.getBiome( vector.getBlockX(), vector.getBlockY(), vector.getBlockZ() );
    }

    public synchronized void setBiome( Vector vector, Dimension dimension, Biome biome ) {
        Chunk chunk = this.getChunk( vector.getChunkX(), vector.getChunkZ(), dimension );
        chunk.setBiome( vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), biome );
    }

    public synchronized void setBiome( int x, int y, int z, Dimension dimension, Biome biome ) {
        this.setBiome( new Vector( x, y, z ), dimension, biome );
    }

    public void setBiome( Vector vector, Biome biome ) {
        this.setBiome( vector, Dimension.OVERWORLD, biome );
    }

    public void setBiome( int x, int y, int z, Biome biome ) {
        this.setBiome( new Vector( x, y, z ), biome );
    }

    public synchronized boolean isChunkLoaded( int chunkX, int chunkZ, Dimension dimension ) {
        return this.chunkManagers.get( dimension ).isChunkLoaded( chunkX, chunkZ );
    }

    public synchronized Chunk getChunk( int chunkX, int chunkZ, Dimension dimension ) {
        return this.chunkManagers.get( dimension ).getChunk( chunkX, chunkZ );
    }

    public synchronized Chunk getLoadedChunk( int chunkX, int chunkZ, Dimension dimension ) {
        return this.chunkManagers.get( dimension ).getLoadedChunk( chunkX, chunkZ );
    }

    public synchronized Chunk getLoadedChunk( Vector vector, Dimension dimension ) {
        return this.getLoadedChunk( vector.getChunkX(), vector.getChunkZ(), dimension );
    }

    public synchronized Chunk getLoadedChunk( long hash, Dimension dimension ) {
        return this.chunkManagers.get( dimension ).getLoadedChunk( hash );
    }

    public synchronized Set<Chunk> getLoadedChunks( Dimension dimension ) {
        return this.chunkManagers.get( dimension ).getLoadedChunks();
    }

    public CompletableFuture<Chunk> getChunkFuture( int chunkX, int chunkZ, Dimension dimension ) {
        return this.chunkManagers.get( dimension ).getChunkFuture( chunkX, chunkZ );
    }

    public Set<Chunk> getChunks( Dimension dimension ) {
        return this.chunkManagers.get( dimension ).getLoadedChunks();
    }

    public CompletableFuture<Void> saveChunk( Chunk chunk ) {
        return this.levelDB.saveChunk( chunk );
    }

    public CompletableFuture<Void> saveChunks( Dimension dimension ) {
        return this.chunkManagers.get( dimension ).saveChunks();
    }

    public CompletableFuture<Chunk> readChunk( Chunk chunk ) {
        return this.levelDB.readChunk( chunk );
    }

    public Set<Player> getChunkPlayers( int chunkX, int chunkZ, Dimension dimension ) {
        Chunk chunk = this.getLoadedChunk( chunkX, chunkZ, dimension );
        return chunk == null ? ImmutableSet.of() : new HashSet<>( chunk.getPlayers() );
    }

    public void sendChunkPacket( int chunkX, int chunkZ, BedrockPacket packet ) {
        for ( Entity entity : this.entities.values() ) {
            if ( entity instanceof Player player ) {
                if ( player.isChunkLoaded( chunkX, chunkZ ) ) {
                    player.getPlayerConnection().sendPacket( packet );
                }
            }
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
            player.getPlayerConnection().sendPacket( levelSoundEventPacket );
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
            player.getPlayerConnection().sendPacket( levelEventPacket );
        } else {
            this.sendChunkPacket( position.getChunkX(), position.getChunkZ(), levelEventPacket );
        }
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
            player.getPlayerConnection().sendPacket( levelEventPacket );
        } else {
            this.sendChunkPacket( position.getChunkX(), position.getChunkZ(), levelEventPacket );
        }
    }

    private Vector getRelative( Vector blockPosition, Vector position ) {
        float x = blockPosition.getX() + position.getX();
        float y = blockPosition.getY() + position.getY();
        float z = blockPosition.getZ() + position.getZ();
        return new Vector( x, y, z, blockPosition.getDimension() );
    }

    public Vector getSidePosition( Vector blockPosition, BlockFace blockFace ) {
        return switch ( blockFace ) {
            case DOWN -> this.getRelative( blockPosition, new Vector( 0, -1, 0 ) );
            case UP -> this.getRelative( blockPosition, new Vector( 0, 1, 0 ) );
            case NORTH -> this.getRelative( blockPosition, new Vector( 0, 0, -1 ) );
            case SOUTH -> this.getRelative( blockPosition, new Vector( 0, 0, 1 ) );
            case WEST -> this.getRelative( blockPosition, new Vector( -1, 0, 0 ) );
            case EAST -> this.getRelative( blockPosition, new Vector( 1, 0, 0 ) );
        };
    }

    public void close() {
        this.levelDB.close();
        this.saveLevelFile();
    }

    public void save() {
        for ( Dimension dimension : Dimension.values() ) {
            this.saveChunks( dimension ).whenComplete( ( unused, throwable ) -> {
            } );
        }
        this.saveLevelFile();
    }

    public Collection<Entity> getNearbyEntities( AxisAlignedBB boundingBox, Dimension dimension, Entity entity ) {
        Set<Entity> targetEntity = new HashSet<>();

        int minX = (int) FastMath.floor( ( boundingBox.getMinX() - 2 ) / 16 );
        int maxX = (int) FastMath.ceil( ( boundingBox.getMaxX() + 2 ) / 16 );
        int minZ = (int) FastMath.floor( ( boundingBox.getMinZ() - 2 ) / 16 );
        int maxZ = (int) FastMath.ceil( ( boundingBox.getMaxZ() + 2 ) / 16 );

        for ( int x = minX; x <= maxX; ++x ) {
            for ( int z = minZ; z <= maxZ; ++z ) {
                Chunk chunk = this.getLoadedChunk( x, z, dimension );
                if ( chunk != null ) {
                    for ( Entity iterateEntities : chunk.getEntities() ) {
                        if ( iterateEntities == null ) {
                            continue;
                        }
                        if ( !iterateEntities.equals( entity ) ) {
                            AxisAlignedBB bb = iterateEntities.getBoundingBox();
                            if ( bb.intersectsWith( boundingBox ) ) {
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

    public List<AxisAlignedBB> getCollisionCubes( Entity entity, AxisAlignedBB boundingBox, boolean includeEntities ) {
        int minX = (int) FastMath.floor( boundingBox.getMinX() );
        int minY = (int) FastMath.floor( boundingBox.getMinY() );
        int minZ = (int) FastMath.floor( boundingBox.getMinZ() );
        int maxX = (int) FastMath.ceil( boundingBox.getMaxX() );
        int maxY = (int) FastMath.ceil( boundingBox.getMaxY() );
        int maxZ = (int) FastMath.ceil( boundingBox.getMaxZ() );

        List<AxisAlignedBB> collides = new ArrayList<>();

        for ( int z = minZ; z <= maxZ; ++z ) {
            for ( int x = minX; x <= maxX; ++x ) {
                for ( int y = minY; y <= maxY; ++y ) {
                    Block block = this.getBlock( new Vector( x, y, z ), 0 );
                    if ( !block.canPassThrough() && block.getBoundingBox().intersectsWith( boundingBox ) ) {
                        collides.add( block.getBoundingBox() );
                    }
                }
            }
        }

        if ( includeEntities ) {
            for ( Entity nearbyEntity : this.getNearbyEntities( boundingBox.grow( 0.25f, 0.25f, 0.25f ), entity.getDimension(), entity ) ) {
                if ( !nearbyEntity.canPassThrough() ) {
                    collides.add( nearbyEntity.getBoundingBox().clone() );
                }
            }
        }

        return collides;
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

    public EntityItem dropItem( Item item, Vector location, Vector velocity ) {
        if ( velocity == null ) {
            velocity = new Vector(
                    (float) ( ThreadLocalRandom.current().nextDouble() * 0.2f - 0.1f ),
                    0.2f,
                    (float) ( ThreadLocalRandom.current().nextDouble() * 0.2f - 0.1f ) );
        }

        EntityItem entityItem = Entity.create( EntityType.ITEM );
        entityItem.setItem( item );
        entityItem.setVelocity( velocity, false );
        entityItem.setLocation( new Location( this, location ) );
        entityItem.setPickupDelay( 1, TimeUnit.SECONDS );
        return entityItem;
    }

    public void sendWorldPacket( BedrockPacket packet ) {
        for ( Player player : this.getPlayers() ) {
            player.getPlayerConnection().sendPacket( packet );
        }
    }

    public void sendDimensionPacket( BedrockPacket packet, Dimension dimension ) {
        for ( Player player : this.getPlayers() ) {
            if ( player.getDimension().equals( dimension ) ) {
                player.getPlayerConnection().sendPacket( packet );
            }
        }
    }

    public Entity getEntity( long entityId ) {
        return this.entities.get( entityId );
    }
}
