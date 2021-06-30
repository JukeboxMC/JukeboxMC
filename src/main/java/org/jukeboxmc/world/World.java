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
import org.jukeboxmc.block.BlockLiquid;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.UpdateReason;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.block.BlockBreakEvent;
import org.jukeboxmc.event.block.BlockPlaceEvent;
import org.jukeboxmc.event.player.PlayerBucketEmptyEvent;
import org.jukeboxmc.event.player.PlayerBucketFillEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.AxisAlignedBB;
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
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

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

    private Queue<BlockUpdateNormal> blockUpdateNormals = new ConcurrentLinkedQueue<>();

    private Map<Dimension, Map<Long, Chunk>> chunkMap;
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
        this.spawnLocation = new Location( this, 0, 4 + 1.62f, 0 );
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

    public WorldGenerator getWorldGenerator() {
        return this.worldGenerator;
    }

    public void addPlayer( Player player ) {
        this.players.put( player.getEntityId(), player );
    }

    public void removePlayer( Player player ) {
        this.players.remove( player.getEntityId() );
    }

    public Map<Long, Chunk> getChunkMap( Dimension dimension ) {
        return this.chunkMap.get( dimension );
    }

    public Collection<Player> getPlayers() {
        return this.players.values();
    }

    public Location getSafeSpawnLocation( Dimension dimension ) {
        if ( this.prepareSpawnLocaion ) {
            return this.spawnLocation;
        }
        int airRuntimeId = new BlockAir().getRuntimeId();
        Vector blockPosition = new Vector( this.spawnLocation.getFloorX(), 0, this.spawnLocation.getFloorZ() );
        Chunk chunk = this.getChunk( this.spawnLocation.getFloorX() >> 4, this.spawnLocation.getFloorX() >> 4, dimension );
        for ( int i = 255; i > 0; i-- ) {
            blockPosition.setY( i );
            if ( chunk.getRuntimeId( blockPosition.getFloorX(), blockPosition.getFloorY(), blockPosition.getFloorZ(), 0 ) != airRuntimeId ) {
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

    public CompletableFuture<Chunk> loadChunk( int chunkX, int chunkZ, Dimension dimension ) {
        long chunkHash = Utils.toLong( chunkX, chunkZ );
        Map<Long, Chunk> chunkMap = this.chunkMap.computeIfAbsent( dimension, o -> new ConcurrentHashMap<>() );
        if ( !chunkMap.containsKey( chunkHash ) ) {
            return CompletableFuture.supplyAsync( () -> {
                Chunk chunk = new Chunk( this, chunkX, chunkZ, dimension );

                byte[] version = this.db.get( Utils.getKey( chunkX, chunkZ, dimension, (byte) 0x2C ) );
                if ( version == null ) {
                    WorldGenerator worldGenerator;
                    if ( this.worldGenerator != null ) {
                        worldGenerator = this.worldGenerator;
                    } else {
                        worldGenerator = Server.getInstance().getOverworldGenerator();
                    }
                    worldGenerator.generate( chunk );
                } else {
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
                }
                chunkMap.put( chunkHash, chunk );
                return chunk;
            } );
        } else {
            return CompletableFuture.supplyAsync( () -> chunkMap.get( chunkHash ) );
        }
    }

    public Chunk getChunk( int chunkX, int chunkZ, Dimension dimension ) {
        return this.loadChunk( chunkX, chunkZ, dimension ).join();
    }

    public void save() {
        this.chunkMap.forEach( ( dimension, chunkMap ) -> chunkMap.values().forEach( chunk -> chunk.save( this.db ) ) );

        this.saveLevelDatFile();
        Server.getInstance().getLogger().info( "The world \"" + this.name + "\" was saved successfully" );
    }

    @SneakyThrows
    public void close() {
        this.save();
        this.db.close();
    }

    public int getHeightMap( int x, int z, Dimension dimension ) {
        return this.getChunk( x >> 4, z >> 4, dimension ).getHeightMap( x, z );
    }

    public Block getBlock( int x, int y, int z ) {
        return this.getBlock( new Vector( x, y, z ), 0 );
    }

    public Block getBlock( Vector location ) {
        return this.getBlock( location, 0 );
    }

    public Block getBlock( Vector location, int layer ) {
        Chunk chunk = this.getChunk( location.getFloorX() >> 4, location.getFloorZ() >> 4, location.getDimension() );
        return chunk.getBlock( location.getFloorX(), location.getFloorY(), location.getFloorZ(), layer );
    }

    public Block getBlockAt( int x, int y, int z, Dimension dimension, int layer ) {
        return this.getBlock( new Vector( x, y, z, dimension ), layer );
    }

    public Block getBlockAt( int x, int y, int z, Dimension dimension ) {
        return this.getBlockAt( x, y, z, dimension, 0 );
    }

    public int getHighestBlockAt( Vector vector, Dimension dimension ) {
        return this.getHeighestBlockAt( vector.getFloorX(), vector.getFloorZ(), dimension );
    }

    public int getHeighestBlockAt( int x, int z, Dimension dimension ) {
        for ( int y = dimension.equals( Dimension.OVERWORLD ) ? 255 : 120; y >= 0; y-- ) {
            BlockType blockType = this.getBlockAt( x, y, z, dimension ).getBlockType();
            if ( !blockType.equals( BlockType.AIR ) ) {
                this.setHeightMap( x, z, dimension, y );
                return y;
            }
        }
        return 256;
    }

    public Vector getSafeLocationAt( int x, int z, Dimension dimension ) {
        for ( int y = dimension.equals( Dimension.OVERWORLD ) ? 252 : 124; y >= 0; y-- ) {
            if ( getBlockAt( x, y, z, dimension ).isSolid() ) {
                if ( !getBlockAt( x, y + 1, z, dimension ).isSolid() ) {
                    if ( !getBlockAt( x, y + 2, z, dimension ).isSolid() ) {
                        return new Vector( x, y + 1, z );
                    }
                }
            }
        }
        return new Vector( x, 0, z );
    }

    public void setHeightMap( int x, int z, Dimension dimension, int value ) {
        this.getChunk( x >> 4, z >> 4, dimension ).setHeightMap( x, z, value );
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
        Chunk chunk = this.getChunk( location.getFloorX() >> 4, location.getFloorZ() >> 4, dimension );
        chunk.setBlock( location.getFloorX(), location.getFloorY(), location.getFloorZ(), layer, block );

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
            chunk.removeBlockEntity( location.getFloorX(), location.getFloorY(), location.getFloorZ() );
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

    public void setBlockEntity( Vector location, BlockEntity blockEntity, Dimension dimension ) {
        Chunk chunk = this.getChunk( location.getFloorX() >> 4, location.getFloorZ() >> 4, dimension );
        chunk.setBlockEntity( location.getFloorX(), location.getFloorY(), location.getFloorZ(), blockEntity );
    }

    public BlockEntity getBlockEntity( Vector location, Dimension dimension ) {
        Chunk chunk = this.getChunk( location.getFloorX() >> 4, location.getFloorZ() >> 4, dimension );
        return chunk.getBlockEntity( location.getFloorX(), location.getFloorY(), location.getFloorZ() );
    }

    public void removeBlockEntity( Vector location, Dimension dimension ) {
        Chunk chunk = this.getChunk( location.getFloorX() >> 4, location.getFloorZ() >> 4, dimension );
        chunk.removeBlockEntity( location.getFloorX(), location.getFloorY(), location.getFloorZ() );
    }

    public Biome getBiome( Vector location, Dimension dimension ) {
        Chunk chunk = this.getChunk( location.getFloorX() >> 4, location.getFloorZ() >> 4, dimension );
        return chunk.getBiome( location.getFloorX() & 15, location.getFloorZ() & 15 );
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

    public Location getSpawnLocation( Dimension dimension ) {
        if ( dimension.equals( Dimension.OVERWORLD ) ) {
            return new Location( this, this.spawnLocation.divide( 8, 8 ) );
        } else {
            return new Location( this, this.spawnLocation );
        }
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

    public Collection<Entity> getNearbyEntities( AxisAlignedBB bb, Dimension dimension ) {
        Set<Entity> targetEntity = new HashSet<>();

        int minX = (int) Math.floor( ( bb.getMinX() - 2 ) / 16 );
        int maxX = (int) Math.ceil( ( bb.getMaxX() + 2 ) / 16 );
        int minZ = (int) Math.floor( ( bb.getMinZ() - 2 ) / 16 );
        int maxZ = (int) Math.ceil( ( bb.getMaxZ() + 2 ) / 16 );

        for ( int x = minX; x <= maxX; ++x ) {
            for ( int z = minZ; z <= maxZ; ++z ) {
                Chunk chunk = this.getChunk( x, z, dimension );
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

        boolean interact = false;
        if ( !player.isSneaking() ) {
            interact = clickedBlock.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
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
                Collection<Entity> nearbyEntities = this.getNearbyEntities( placedBlock.getBoundingBox(), location.getDimension() );
                if ( !nearbyEntities.isEmpty() ) {
                    return false;
                }
                AxisAlignedBB boundingBox = player.getBoundingBox();
                if ( placedBlock.getBoundingBox().intersectsWith( boundingBox ) ) {
                    return false;
                }
            }

            String itemName = itemInHand.getClass().getSimpleName();
            boolean isNormalBucket = itemInHand.getItemType().equals( ItemType.BUCKET );

            if ( itemName.endsWith( "Bucket" ) ) {
                if ( placedBlock.getBlockType().equals( BlockType.AIR )
                        && clickedBlock instanceof BlockLiquid && isNormalBucket ) {
                    PlayerBucketFillEvent playerBucketFillEvent = new PlayerBucketFillEvent( player, itemInHand,
                            player.getInventory().getItemInHand(), clickedBlock, placedBlock );

                    Server.getInstance().getPluginManager().callEvent( playerBucketFillEvent );

                    if ( playerBucketFillEvent.isCancelled() ) {
                        return false;
                    }

                    clickedBlock = playerBucketFillEvent.getClickedBlock();
                    placedBlock = playerBucketFillEvent.getPlacedBlock();
                }

                if ( placedBlock instanceof BlockLiquid && !isNormalBucket ) {
                    PlayerBucketEmptyEvent playerBucketEmptyEvent = new PlayerBucketEmptyEvent( player, itemInHand,
                            player.getInventory().getItemInHand(), clickedBlock, placedBlock );

                    Server.getInstance().getPluginManager().callEvent( playerBucketEmptyEvent );

                    if ( playerBucketEmptyEvent.isCancelled() ) {
                        return false;
                    }

                    clickedBlock = playerBucketEmptyEvent.getClickedBlock();
                    placedBlock = playerBucketEmptyEvent.getPlacedBlock();
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

    public Vector getSidePosition( Vector blockPosition, BlockFace blockFace ) {
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

    private Vector getRelative( Vector blockPosition, Vector position ) {
        float x = blockPosition.getX() + position.getX();
        float y = blockPosition.getY() + position.getY();
        float z = blockPosition.getZ() + position.getZ();
        return new Vector( x, y, z, blockPosition.getDimension() );
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
            //Drop Item
        }

        this.playSound( breakPosition, LevelSound.BREAK, breakBlock.getRuntimeId() );
        this.sendLevelEvent( breakPosition, LevelEvent.PARTICLE_DESTROY_BLOCK, breakBlock.getRuntimeId() );
    }

    public void sendBlockUpdate( Block block ) {
        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setBlockId( block.getRuntimeId() );
        updateBlockPacket.setPosition( block.getLocation() );
        updateBlockPacket.setFlags( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        updateBlockPacket.setLayer( block.getLayer() );
        this.sendDimensionPacket( updateBlockPacket, block.getLocation().getDimension() );
    }

    public void sendDimensionPacket( Packet packet, Dimension dimension ) {
        for ( Player player : this.getPlayers() ) {
            if ( player.getDimension().equals( dimension ) ) {
                player.getPlayerConnection().sendPacket( packet );
            }
        }
    }

    public void sendWorldPacket( Packet packet ) {
        for ( Player player : this.getPlayers() ) {
            player.getPlayerConnection().sendPacket( packet );
        }
    }

    public void scheduleBlockUpdate( Location location, long delay ) {
        this.blockUpdateList.addElement( this.server.getCurrentTick() + delay, location );
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
}
