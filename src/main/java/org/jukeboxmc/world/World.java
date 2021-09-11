package org.jukeboxmc.world;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAir;
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
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.*;
import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.generator.WorldGenerator;
import org.jukeboxmc.world.leveldb.LevelDBWorld;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class World extends LevelDBWorld {

    private final String name;
    private final Server server;
    private final WorldGenerator worldGenerator;
    private final BlockUpdateList blockUpdateList;

    public final Map<Dimension, HashMap<Long, Chunk>> chunkMap = new ConcurrentHashMap<>();

    protected final Queue<BlockUpdateNormal> blockUpdateNormals = new ConcurrentLinkedQueue<>();

    protected final Map<GameRule<?>, Object> gamerules = new ConcurrentHashMap<>();
    protected final Map<Long, Entity> entities = new ConcurrentHashMap<>();

    private final ExecutorService chunkThread = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors() );

    public World( String name, Server server, WorldGenerator worldGenerator ) {
        super( name );
        this.name = name;
        this.server = server;
        this.worldGenerator = worldGenerator;

        this.blockUpdateList = new BlockUpdateList();
        this.spawnLocation = new Location( this, 0, 73, 0 );
        this.difficulty = Difficulty.NORMAL;

        this.chunkMap.computeIfAbsent( Dimension.OVERWORLD, o -> new HashMap<>() );
        this.chunkMap.computeIfAbsent( Dimension.NETHER, o -> new HashMap<>() );
        this.chunkMap.computeIfAbsent( Dimension.THE_END, o -> new HashMap<>() );

        this.initGameRules();
        this.saveLevelDatFile();
    }

    public void update( long currentTick ) {
        for ( Entity entity : this.entities.values() ) {
            entity.update( currentTick );
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
                NBTInputStream reader = NbtUtils.createReaderLE( new ByteBufInputStream( allocate ) );
                compound = ( (NbtMap) reader.readTag() ).toBuilder();
            }
            levelDat.delete();
        } else {
            compound = NbtMap.builder();
        }

        compound.putInt( "StorageVersion", 8 );

        compound.putInt( "SpawnX", this.spawnLocation.getBlockX() );
        compound.putInt( "SpawnY", this.spawnLocation.getBlockY() );
        compound.putInt( "SpawnZ", this.spawnLocation.getBlockZ() );
        compound.putInt( "Difficulty", this.difficulty.ordinal() );

        compound.putString( "LevelName", this.name );
        compound.putLong( "Time", 0 ); //TODO


        for ( Map.Entry<GameRule<?>, Object> entry : this.gamerules.entrySet() ) {
            compound.put( entry.getKey().getName(), entry.getKey().toCompoundValue( entry.getValue() ) );
        }

        try ( FileOutputStream stream = new FileOutputStream( levelDat ) ) {
            stream.write( new byte[8] );

            ByteBuf data = PooledByteBufAllocator.DEFAULT.heapBuffer();
            NBTOutputStream writer = NbtUtils.createWriterLE( new ByteBufOutputStream( data ) );
            writer.writeTag( compound.build() );
            stream.write( data.array(), data.arrayOffset(), data.readableBytes() );
            data.release();
        }
    }

    public Chunk loadChunkSync( int chunkX, int chunkZ, Dimension dimension ) {
        if ( !this.chunkMap.get( dimension ).containsKey( Utils.toLong( chunkX, chunkZ ) ) ) {
            Chunk chunk = new Chunk( this, chunkX, chunkZ, dimension );

            byte[] version = this.db.get( Utils.getKey( chunkX, chunkZ, dimension, (byte) 0x2C ) );
            if ( version == null ) {
                version = this.db.get( Utils.getKey( chunkX, chunkZ, dimension, (byte) 0x76 ) );
            }

            if ( version == null ) {
                WorldGenerator worldGenerator;
                if ( this.worldGenerator != null ) {
                    worldGenerator = this.worldGenerator;
                } else {
                    worldGenerator = Server.getInstance().getOverworldGenerator();
                }
                worldGenerator.generate( chunk );
                return chunk;
            }

            byte[] finalized = this.db.get( Utils.getKey( chunkX, chunkZ, dimension, (byte) 0x36 ) );

            chunk.chunkVersion = version[0];
            chunk.setPopulated( finalized == null || finalized[0] == 2 );

            for ( int sectionY = 0; sectionY < 16; sectionY++ ) {
                byte[] chunkData = this.db.get( Utils.getSubChunkKey( chunkX, chunkZ, dimension, (byte) 0x2f, (byte) sectionY ) );

                if ( chunkData != null ) {
                    chunk.checkAndCreateSubChunks( sectionY );
                    chunk.loadSection( chunk.subChunks[sectionY], chunkData );
                }
            }

            byte[] blockEntitys = this.db.get( Utils.getKey( chunkX, chunkZ, dimension, (byte) 0x31 ) );
            if ( blockEntitys != null ) {
                chunk.loadBlockEntitys( chunk, blockEntitys );
            }

            byte[] biomes = this.db.get( Utils.getKey( chunkX, chunkZ, dimension, (byte) 0x2d ) );
            if ( biomes != null ) {
                chunk.loadHeightAndBiomes( biomes );
            }

            val map = this.chunkMap.get( dimension );
            map.put( chunk.toChunkHash(), chunk );
            return chunk;
        } else {
            val map = this.chunkMap.get( dimension );
            return map.get( Utils.toLong( chunkX, chunkZ ) );
        }
    }

    public CompletableFuture<Chunk> loadChunkAsync( int chunkX, int chunkZ, Dimension dimension ) {
        return CompletableFuture.supplyAsync( () -> {
            if ( !this.chunkMap.get( dimension ).containsKey( Utils.toLong( chunkX, chunkZ ) ) ) {
                Chunk chunk = new Chunk( this, chunkX, chunkZ, dimension );

                byte[] version = this.db.get( Utils.getKey( chunkX, chunkZ, dimension, (byte) 0x2C ) );
                if ( version == null ) {
                    version = this.db.get( Utils.getKey( chunkX, chunkZ, dimension, (byte) 0x76 ) );
                }

                if ( version == null ) {
                    WorldGenerator worldGenerator;
                    if ( this.worldGenerator != null ) {
                        worldGenerator = this.worldGenerator;
                    } else {
                        worldGenerator = Server.getInstance().getOverworldGenerator();
                    }
                    worldGenerator.generate( chunk );
                    return chunk;
                }

                byte[] finalized = this.db.get( Utils.getKey( chunkX, chunkZ, dimension, (byte) 0x36 ) );

                chunk.chunkVersion = version[0];
                chunk.setPopulated( finalized == null || finalized[0] == 2 );

                for ( int sectionY = 0; sectionY < 16; sectionY++ ) {
                    byte[] chunkData = this.db.get( Utils.getSubChunkKey( chunkX, chunkZ, dimension, (byte) 0x2f, (byte) sectionY ) );

                    if ( chunkData != null ) {
                        chunk.checkAndCreateSubChunks( sectionY );
                        chunk.loadSection( chunk.subChunks[sectionY], chunkData );
                    }
                }

                byte[] blockEntitys = this.db.get( Utils.getKey( chunkX, chunkZ, dimension, (byte) 0x31 ) );
                if ( blockEntitys != null ) {
                    chunk.loadBlockEntitys( chunk, blockEntitys );
                }

                byte[] biomes = this.db.get( Utils.getKey( chunkX, chunkZ, dimension, (byte) 0x2d ) );
                if ( biomes != null ) {
                    chunk.loadHeightAndBiomes( biomes );
                }

                val map = this.chunkMap.get( dimension );
                map.put( chunk.toChunkHash(), chunk );
                return chunk;
            } else {
                val map = this.chunkMap.get( dimension );
                return map.get( Utils.toLong( chunkX, chunkZ ) );
            }
        }, this.chunkThread );
    }

    public Chunk getChunk( int chunkX, int chunkZ, Dimension dimension ) {
        return this.loadChunkSync( chunkX, chunkZ, dimension );
    }

    public void clearChunks() {
        this.chunkMap.clear();
    }

    public void prepareSpawnRegion() {
        int spawnRadius = 4;

        int chunkX = Utils.blockToChunk( this.spawnLocation.getBlockX() );
        int chunkZ = Utils.blockToChunk( this.spawnLocation.getBlockZ() );

        for ( int i = chunkX - spawnRadius; i <= chunkX + spawnRadius; i++ ) {
            for ( int j = chunkZ - spawnRadius; j <= chunkZ + spawnRadius; j++ ) {
                this.loadChunkSync( chunkX, chunkZ, Dimension.OVERWORLD );
            }
        }
    }

    public void save() {
        this.chunkMap.forEach( ( dimension, chunkMap ) -> chunkMap.values().forEach( chunk -> chunk.save( this.db ) ) );
        this.saveLevelDatFile();
        Server.getInstance().getLogger().info( "The world \"" + this.name + "\" was saved successfully" );
    }

    public void close() {
        this.save();
        try {
            this.db.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return this.name;
    }

    public Server getServer() {
        return this.server;
    }

    public WorldGenerator getWorldGenerator() {
        return this.worldGenerator;
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

    public void initGameRules() {
        this.setGameRule( GameRule.COMMAND_BLOCK_OUTPUT, true );
        this.setGameRule( GameRule.DO_DAYLIGHT_CYCLE, true );
        this.setGameRule( GameRule.DO_ENTITY_DROPS, true );
        this.setGameRule( GameRule.DO_FIRE_TICK, true );
        this.setGameRule( GameRule.DO_IMMEDIATE_RESPAWN, true );
        this.setGameRule( GameRule.DO_MOB_LOOT, true );
        this.setGameRule( GameRule.DO_MOB_SPAWNING, true );
        this.setGameRule( GameRule.DO_TILE_DROPS, true );
        this.setGameRule( GameRule.DO_WEATHER_CYCLE, true );
        this.setGameRule( GameRule.DROWNING_DAMAGE, true );
        this.setGameRule( GameRule.FALL_DAMAGE, true );
        this.setGameRule( GameRule.FIRE_DAMAGE, true );
        this.setGameRule( GameRule.KEEP_INVENTORY, true );
        this.setGameRule( GameRule.MOB_GRIEFING, true );
        this.setGameRule( GameRule.NATURAL_REGENERATION, true );
        this.setGameRule( GameRule.PVP, true );
        this.setGameRule( GameRule.RANDOM_TICK_SPEED, 1 );
        this.setGameRule( GameRule.SEND_COMMAND_FEEDBACK, true );
        this.setGameRule( GameRule.SHOW_COORDINATES, true );
        this.setGameRule( GameRule.TNT_EXPLODES, true );
        this.setGameRule( GameRule.SHOW_DEATH_MESSAGE, true );
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

    public void addEntity( Entity entity ) {
        this.entities.put( entity.getEntityId(), entity );
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
    }

    public Location getSpawnLocation( Dimension dimension ) {
        if ( dimension.equals( Dimension.OVERWORLD ) ) {
            return new Location( this, this.spawnLocation.divide( 8, 0, 8 ) );
        } else {
            return new Location( this, this.spawnLocation );
        }
    }

    //========= Blocks =========

    public Block getBlock( Vector location ) {
        return this.getBlock( location, 0 );
    }

    public Block getBlock( Vector location, int layer ) {
        Chunk chunk = this.getChunk( location.getBlockX() >> 4, location.getBlockZ() >> 4, location.getDimension() );
        return chunk.getBlock( location.getBlockX(), location.getBlockY(), location.getBlockZ(), layer );
    }

    public Block getBlock( int x, int y, int z ) {
        return this.getBlock( new Vector( x, y, z ), 0 );
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        return this.getBlock( new Vector( x, y, z ), layer );
    }

    public Block getBlockAt( int x, int y, int z, Dimension dimension ) {
        return this.getBlockAt( x, y, z, dimension, 0 );
    }

    public Block getBlockAt( int x, int y, int z, Dimension dimension, int layer ) {
        return this.getBlock( new Vector( x, y, z, dimension ), layer );
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
        block.setPlaced( true );

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

        BlockBreakEvent blockBreakEvent = new BlockBreakEvent( player, breakBlock, breakBlock.getDrops() );
        Server.getInstance().getPluginManager().callEvent( blockBreakEvent );

        if ( blockBreakEvent.isCancelled() ) {
            breakBlock.sendBlockUpdate( player );
            return;
        }

        if ( breakBlock.onBlockBreak( breakPosition ) ) {
            //TODO Drop Item
        }

        this.playSound( breakPosition, LevelSound.BREAK, breakBlock.getRuntimeId() );
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

        if ( !interact && itemInHand.useOnBlock( player, clickedBlock, location ) ) {
            return true;
        }

        if ( itemInHand instanceof ItemAir ) {
            return interact;
        }

        if ( !interact || player.isSneaking() ) {
            if ( !replacedBlock.canBeReplaced( placedBlock ) ) {
                return false;
            }

            if ( clickedBlock.canBeReplaced( placedBlock ) ) {
                placePosition = blockPosition;
                clickedBlock.onBlockBreak( placePosition );
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

            BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent( player, placedBlock, replacedBlock, clickedBlock );
            Server.getInstance().getPluginManager().callEvent( blockPlaceEvent );

            if ( blockPlaceEvent.isCancelled() ) {
                return false;
            }

            boolean success = blockPlaceEvent.getPlacedBlock().placeBlock( player, this, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
            if ( success ) {
                this.playSound( placePosition, LevelSound.PLACE, placedBlock.getRuntimeId() );
            }
            return success;
        }
        return interact;
    }

    //========= BlockEntitys =========

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

    public Collection<BlockEntity> getBlockEntitys( Vector location, Dimension dimension ) {
        Chunk chunk = this.getChunk( location.getBlockX() >> 4, location.getBlockZ() >> 4, dimension );
        return chunk.getBlockEntitys();
    }

    //========= Biome =========

    public Biome getBiome( Vector location, Dimension dimension ) {
        Chunk chunk = this.getChunk( location.getBlockX() >> 4, location.getBlockZ() >> 4, dimension );
        return chunk.getBiome( location.getBlockX() & 15, location.getBlockZ() & 15 );
    }

    //========= With Packets =========

    public void sendWorldPacket( Packet packet ) {
        for ( Player player : this.getPlayers() ) {
            player.getPlayerConnection().sendPacket( packet );
        }
    }

    public void sendDimensionPacket( Packet packet, Dimension dimension ) {
        for ( Player player : this.getPlayers() ) {
            if ( player.getDimension().equals( dimension ) ) {
                player.getPlayerConnection().sendPacket( packet );
            }
        }
    }

    public void sendChunkPacket( int chunkX, int chunkZ, Packet packet ) {
        for ( Player player : this.getPlayers() ) {
            if ( player != null && player.isChunkLoaded( chunkX, chunkZ ) ) {
                player.getPlayerConnection().sendPacket( packet );
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

    public void sendLevelEvent( Vector position, LevelEvent levelEvent, int runtimeId ) {
        this.sendLevelEvent( null, position, levelEvent, runtimeId );
    }

    public void sendLevelEvent( Player player, Vector position, LevelEvent levelEvent, int runtimeId ) {
        LevelEventPacket levelEventPacket = new LevelEventPacket();
        levelEventPacket.setPosition( position );
        levelEventPacket.setLevelEvent( levelEvent );
        levelEventPacket.setData( runtimeId );

        if ( player != null ) {
            player.getPlayerConnection().sendPacket( levelEventPacket );
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
            player.getPlayerConnection().sendPacket( levelSoundEventPacket );
        }
    }

    //========= Other =========

    public EntityItem dropItem( Item item, Vector location, Vector velocity ) {
        if ( velocity == null ) {
            velocity = new Vector( ThreadLocalRandom.current().nextFloat() * 0.2f - 0.1f,
                    0.2f, ThreadLocalRandom.current().nextFloat() * 0.2f - 0.1f );
        }

        EntityItem entityItem = new EntityItem();
        entityItem.setItem( item );
        entityItem.setVelocity( velocity, false );
        entityItem.setLocation( new Location( this, location ) );
        entityItem.setPickupDelay( 20, TimeUnit.MILLISECONDS );
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
                                targetEntity.add( iterateEntities );
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
