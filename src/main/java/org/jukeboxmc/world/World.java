package org.jukeboxmc.world;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAir;
import org.jukeboxmc.block.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.world.BlockBreakEvent;
import org.jukeboxmc.event.world.BlockPlaceEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.*;
import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.generator.WorldGenerator;
import org.jukeboxmc.world.leveldb.LevelDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class World extends LevelDB {

    private String name;

    private Server server;
    private WorldGenerator worldGenerator;
    private BlockUpdateList blockUpdateList;

    private int worldTime;

    private boolean prepareSpawnLocaion = false;

    private Map<Long, Chunk> chunkMap;
    private Map<Long, Player> players;

    public World( String name, Server server, WorldGenerator worldGenerator ) {
        super( name );
        this.chunkMap = new ConcurrentHashMap<>();
        this.players = new ConcurrentHashMap<>();

        this.name = name;
        this.server = server;
        this.worldGenerator = worldGenerator;
        this.blockUpdateList = new BlockUpdateList();
        this.difficulty = Difficulty.NORMAL;
        this.spawnLocation = new Location( 0, 4 + 1.62f, 0 );
        this.saveLevelDatFile();
    }

    public void update( long currentTick ) {
        this.worldTime++;
        while ( this.worldTime >= 24000 ) {
            this.worldTime -= 24000;
        }

        for ( Player player : this.players.values() ) {
            if ( player != null && player.isSpawned() ) {
                player.getPlayerConnection().sendTime( this.worldTime );
            }
        }

        while ( this.blockUpdateList.getNextTaskTime() < currentTick ) {
            BlockPosition blockPosition = this.blockUpdateList.getNextElement();
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

    public WorldGenerator getWorldGenerator() {
        return this.worldGenerator;
    }

    public void addPlayer( Player player ) {
        this.players.put( player.getEntityId(), player );
    }

    public void removePlayer( Player player ) {
        this.players.remove( player.getEntityId() );
    }

    public Map<Long, Chunk> getChunkMap() {
        return this.chunkMap;
    }

    public Collection<Player> getPlayers() {
        return this.players.values();
    }

    public CompletableFuture<Void> prepareSpawnRegion() {
        return CompletableFuture.supplyAsync( () -> {
            int spawnRadius = Server.getInstance().getViewDistance();

            if ( spawnRadius == 0 ) {
                return null;
            }

            final int chunkX = this.spawnLocation.getFloorX() >> 4;
            final int chunkZ = this.spawnLocation.getFloorZ() >> 4;

            for ( int i = chunkX - spawnRadius; i <= chunkX + spawnRadius; i++ ) {
                for ( int j = chunkZ - spawnRadius; j <= chunkZ + spawnRadius; j++ ) {
                    this.loadChunk( i, j ).join();
                }
            }
            return null;
        } );
    }

    public Location getSafeSpawnLocation() {
        if ( this.prepareSpawnLocaion ) {
            return this.spawnLocation;
        }
        int airRuntimeId = new BlockAir().getRuntimeId();
        BlockPosition blockPosition = new BlockPosition( this.spawnLocation.getFloorX(), 0, this.spawnLocation.getFloorZ() );
        Chunk chunk = this.getChunk( this.spawnLocation.getFloorX() >> 4, this.spawnLocation.getFloorX() >> 4 );
        for ( int i = 255; i > 0; i-- ) {
            blockPosition.setY( i );
            if ( chunk.getRuntimeId( blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), 0 ) != airRuntimeId ) {
                this.spawnLocation.setY( 2.5f + i );
                break;
            }
        }
        this.prepareSpawnLocaion = true;
        return this.spawnLocation;
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

        compound.putInt( "SpawnX", this.spawnLocation.getFloorX() );
        compound.putInt( "SpawnY", this.spawnLocation.getFloorY() );
        compound.putInt( "SpawnZ", this.spawnLocation.getFloorZ() );
        compound.putInt( "Difficulty", this.difficulty.ordinal() );

        compound.putString( "LevelName", this.name );
        compound.putLong( "Time", this.worldTime );


        for ( GameRules gameRules : GameRule.getAll() ) {
            compound.put( gameRules.getName(), gameRules.toCompoundValue() );
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

    public CompletableFuture<Chunk> loadChunk( int chunkX, int chunkZ ) {
        long chunkHash = Utils.toLong( chunkX, chunkZ );

        if ( !this.chunkMap.containsKey( chunkHash ) ) {
            return CompletableFuture.supplyAsync( () -> {
                Chunk chunk = new Chunk( this, chunkX, chunkZ );

                byte[] version = this.db.get( Utils.getKey( chunkX, chunkZ, (byte) 0x2C ) );
                if ( version == null ) {
                    if ( this.worldGenerator != null ) {
                        WorldGenerator worldGenerator = this.worldGenerator;
                        worldGenerator.generate( chunk );
                    } else {
                        WorldGenerator worldGenerator = Server.getInstance().getOverworldGenerator();
                        worldGenerator.generate( chunk );
                    }
                } else {
                    byte[] finalized = this.db.get( Utils.getKey( chunkX, chunkZ, (byte) 0x36 ) );

                    chunk.chunkVersion = version[0];
                    chunk.setPopulated( finalized == null || finalized[0] == 2 );

                    for ( int sectionY = 0; sectionY < 16; sectionY++ ) {
                        byte[] chunkData = this.db.get( Utils.getSubChunkKey( chunkX, chunkZ, (byte) 0x2f, (byte) sectionY ) );

                        if ( chunkData != null ) {
                            chunk.checkAndCreateSubChunks( sectionY );
                            chunk.loadSection( chunk.subChunks[sectionY], chunkData );
                        }
                    }

                    byte[] blockEntitys = this.db.get( Utils.getKey( chunkX, chunkZ, (byte) 0x31 ) );
                    if ( blockEntitys != null ) {
                        chunk.loadBlockEntitys( chunk, blockEntitys );
                    }

                    byte[] biomes = this.db.get( Utils.getKey( chunkX, chunkZ, (byte) 0x2d ) );
                    if ( biomes != null ) {
                        chunk.loadHeightAndBiomes( biomes );
                    }
                }
                this.chunkMap.put( chunkHash, chunk );
                return chunk;
            } );
        } else {
            return CompletableFuture.supplyAsync( () -> this.chunkMap.get( chunkHash ) );
        }
    }

    public Chunk getChunk( int chunkX, int chunkZ ) {
        return this.loadChunk( chunkX, chunkZ ).join();
    }

    public void save() {
        for ( Chunk chunk : this.chunkMap.values() ) {
            chunk.save( this.db );
        }
        this.saveLevelDatFile();
        Server.getInstance().getLogger().info( "The world \"" + this.name + "\" was saved successfully" );
    }

    @SneakyThrows
    public void close() {
        this.save();
        this.db.close();
    }

    public Block getBlock( BlockPosition location ) {
        return this.getBlock( location.toVector(), 0 );
    }

    public Block getBlock( Vector location ) {
        return this.getBlock( location, 0 );
    }

    public Block getBlock( Vector vector, int layer ) {
        Chunk chunk = this.getChunk( vector.getFloorX() >> 4, vector.getFloorZ() >> 4 );
        return chunk.getBlock( vector.getFloorX(), vector.getFloorY(), vector.getFloorZ(), layer );
    }

    public Block getBlockAt( int x, int y, int z ) {
        return this.getBlock( new Vector( x, y, z ), 0 );
    }

    public void setBlock( Vector location, Block block ) {
        this.setBlock( location.toBlockPosition(), block, 0 );
    }

    public void setBlock( BlockPosition location, Block block ) {
        this.setBlock( location, block, 0 );
    }

    public void setBlock( BlockPosition location, Block block, int layer ) {
        Chunk chunk = this.getChunk( location.getX() >> 4, location.getZ() >> 4 );
        chunk.setBlock( location.getX(), location.getY(), location.getZ(), layer, block );

        block.setLocation( new Location( this, location ) );
        block.setLayer( layer );

        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setPosition( location );
        updateBlockPacket.setBlockId( block.getRuntimeId() );
        updateBlockPacket.setFlags( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        updateBlockPacket.setLayer( layer );
        this.sendWorldPacket( updateBlockPacket );

        if ( !block.hasBlockEntity() ) {
            chunk.removeBlockEntity( location.getX(), location.getY(), location.getZ() );
        }
    }

    public void setBlockEntity( BlockPosition location, BlockEntity blockEntity ) {
        Chunk chunk = this.getChunk( location.getX() >> 4, location.getZ() >> 4 );
        chunk.setBlockEntity( location.getX(), location.getY(), location.getZ(), blockEntity );
    }

    public BlockEntity getBlockEntity( BlockPosition location ) {
        Chunk chunk = this.getChunk( location.getX() >> 4, location.getZ() >> 4 );
        return chunk.getBlockEntity( location.getX(), location.getY(), location.getZ() );
    }

    public void removeBlockEntity( BlockPosition location ) {
        Chunk chunk = this.getChunk( location.getX() >> 4, location.getZ() >> 4 );
        chunk.removeBlockEntity( location.getX(), location.getY(), location.getZ() );
    }

    public Biome getBiome( Vector location ) {
        Chunk chunk = this.getChunk( location.getFloorX() >> 4, location.getFloorZ() >> 4 );
        return chunk.getBiome( location.getFloorX() & 15, location.getFloorZ() & 15 );
    }

    public Biome getBiome( BlockPosition location ) {
        Chunk chunk = this.getChunk( location.getX() >> 4, location.getZ() >> 4 );
        return chunk.getBiome( location.getX() & 15, location.getZ() & 15 );
    }

    public void playSound( Location location, LevelSound levelSound ) {
        this.playSound( null, location, levelSound, -1, ":", false, false );
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
            this.sendChunkPacket( position.getFloorX() >> 4, position.getFloorZ() >> 4, levelSoundEventPacket );
        } else {
            player.getPlayerConnection().sendPacket( levelSoundEventPacket );
        }
    }

    public void sendChunkPacket( int chunkX, int chunkZ, Packet packet ) {
        for ( Player player : this.getPlayers() ) {
            if ( player != null ) {
                if ( player.getPlayerConnection().knowsChunk( chunkX, chunkZ ) ) {
                    player.getPlayerConnection().sendPacket( packet );
                }
            }
        }
    }

    public int getWorldTime() {
        return this.worldTime;
    }

    public void setSpawnLocation( Location spawnLocation ) {
        this.spawnLocation = spawnLocation;
    }

    public Location getSpawnLocation() {
        return new Location( this, this.spawnLocation );
    }

    public void setDifficulty( Difficulty difficulty ) {
        this.difficulty = difficulty;

        SetDifficultyPacket setDifficultyPacket = new SetDifficultyPacket();
        setDifficultyPacket.setDifficulty( difficulty );
        this.sendWorldPacket( setDifficultyPacket );
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
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
            this.sendChunkPacket( position.getFloorX() >> 4, position.getFloorZ() >> 4, levelEventPacket );
        }
    }

    public Collection<Entity> getNearbyEntities( AxisAlignedBB bb ) {
        Set<Entity> targetEntity = new HashSet<>();

        int minX = (int) Math.floor( ( bb.getMinX() - 2 ) / 16 );
        int maxX = (int) Math.ceil( ( bb.getMaxX() + 2 ) / 16 );
        int minZ = (int) Math.floor( ( bb.getMinZ() - 2 ) / 16 );
        int maxZ = (int) Math.ceil( ( bb.getMaxZ() + 2 ) / 16 );

        for ( int x = minX; x <= maxX; ++x ) {
            for ( int z = minZ; z <= maxZ; ++z ) {
                Chunk chunk = this.getChunk( x, z );
                if ( chunk != null ) {
                    chunk.iterateEntities( entity -> {
                        AxisAlignedBB boundingBox = entity.getBoundingBox();
                        if ( boundingBox.intersectsWith( bb ) ) {
                            targetEntity.add( entity );
                        }
                    } );
                }
            }
        }
        return targetEntity;
    }

    public boolean useItemOn( Player player, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, BlockFace blockFace ) {
        Block clickedBlock = this.getBlock( blockPosition );
        if ( clickedBlock instanceof BlockAir ) {
            return false;
        }
        Item itemInHand = player.getInventory().getItemInHand();
        Block replacedBlock = this.getBlock( placePosition );
        Block placedBlock = itemInHand.getBlock();
        placedBlock.setLocation( new Location( this, placePosition ) );

        boolean interact = false;
        if ( !player.isSneaking() ) {
            interact = clickedBlock.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
        }

        if ( placedBlock instanceof BlockAir && !interact ) {
            return false;
        }

        if ( ( !interact ) || player.isSneaking() ) {
            if ( !replacedBlock.canBeReplaced() ) {
                return false;
            }

            if ( placedBlock.isSolid() ) {
                Collection<Entity> nearbyEntities = this.getNearbyEntities( placedBlock.getBoundingBox() );
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
                this.playSound( placePosition.toVector(), LevelSound.PLACE, placedBlock.getRuntimeId() );
            }
            return success;
        }

        return interact;
    }

    public BlockPosition getSidePosition( BlockPosition blockPosition, BlockFace blockFace ) {
        switch ( blockFace ) {
            case DOWN:
                return this.getRelative( blockPosition, Vector.DOWN );
            case UP:
                return this.getRelative( blockPosition, Vector.UP );
            case NORTH:
                return this.getRelative( blockPosition, Vector.NORTH );
            case SOUTH:
                return this.getRelative( blockPosition, Vector.SOUTH );
            case WEST:
                return this.getRelative( blockPosition, Vector.WEST );
            case EAST:
                return this.getRelative( blockPosition, Vector.EAST );
        }
        return null;
    }

    private BlockPosition getRelative( BlockPosition blockPosition, Vector position ) {
        float x = blockPosition.getX() + position.getX();
        float y = blockPosition.getY() + position.getY();
        float z = blockPosition.getZ() + position.getZ();
        return new Vector( x, y, z ).toBlockPosition();
    }

    public void breakBlock( Player player, BlockPosition breakPosition, boolean isCreative ) {
        Block breakBlock = this.getBlock( breakPosition );

        BlockBreakEvent blockBreakEvent = new BlockBreakEvent( player, breakBlock, breakBlock.getDrops() );
        Server.getInstance().getPluginManager().callEvent( blockBreakEvent );

        if ( blockBreakEvent.isCancelled() ) {
            breakBlock.sendBlockUpdate( player );
            return;
        }

        if ( breakBlock.onBlockBreak( breakPosition, isCreative ) ) {
            //Drop Item
        }

        this.playSound( breakPosition.toVector(), LevelSound.BREAK, breakBlock.getRuntimeId() );
        this.sendLevelEvent( breakPosition.toVector(), LevelEvent.PARTICLE_DESTROY, breakBlock.getRuntimeId() );
    }

    public void sendBlockUpdate( Block block ) {
        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setBlockId( block.getRuntimeId() );
        updateBlockPacket.setPosition( block.getBlockPosition() );
        updateBlockPacket.setFlags( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        updateBlockPacket.setLayer( block.getLayer() );
        this.sendWorldPacket( updateBlockPacket );
    }

    public void sendWorldPacket( Packet packet ) {
        for ( Player player : this.getPlayers() ) {
            player.getPlayerConnection().sendPacket( packet );
        }
    }

    public void scheduleBlockUpdate( Location location, long delay, TimeUnit timeUnit ) {
        BlockPosition position = location.toBlockPosition();
        long timeValue = this.server.getCurrentTick() + timeUnit.toMillis( delay ) / 50;
        this.blockUpdateList.addElement( timeValue, position );
    }
}
