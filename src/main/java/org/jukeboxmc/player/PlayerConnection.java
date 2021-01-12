package org.jukeboxmc.player;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.adventure.AdventureSettings;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.item.ItemFurnace;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.math.Vector2;
import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.network.raknet.Connection;
import org.jukeboxmc.network.raknet.protocol.EncapsulatedPacket;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.utils.Pair;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Sound;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerConnection {

    private Player player;
    private Server server;
    private Connection connection;

    private Queue<Chunk> chunkSendQueue = new ConcurrentLinkedQueue<>();
    private Queue<Packet> sendQueue = new ConcurrentLinkedQueue<>();

    private Set<Long> loadingChunks = new CopyOnWriteArraySet<>();
    private Set<Long> loadedChunks = new CopyOnWriteArraySet<>();
    private Set<InventoryTransactionPacket> spamCheck = new HashSet<>();

    public PlayerConnection( Player player, Server server, Connection connection ) {
        this.player = player;
        this.server = server;
        this.connection = connection;
    }

    public void update( long timestamp ) {

        this.spamCheck.clear();

        if ( !this.sendQueue.isEmpty() ) {
            this.batchPackets( new ArrayList<>( this.sendQueue ), false );
            this.sendQueue.clear();
        }

        if ( !this.chunkSendQueue.isEmpty() ) {
            Chunk chunk;
            while ( ( chunk = this.chunkSendQueue.poll() ) != null ) {
                long hash = Utils.toLong( chunk.getChunkX(), chunk.getChunkZ() );
                if ( !this.loadingChunks.contains( hash ) ) {
                    this.chunkSendQueue.remove( chunk );
                }
                this.sendChunk( chunk );
            }
        }
        this.needNewChunks( false );
    }

    public Set<InventoryTransactionPacket> getSpamCheck() {
        return this.spamCheck;
    }

    public void sendChunk( Chunk chunk ) {
        try {
            LevelChunkPacket levelChunkPacket = new LevelChunkPacket();
            levelChunkPacket.setChunkX( chunk.getChunkX() );
            levelChunkPacket.setChunkZ( chunk.getChunkZ() );
            levelChunkPacket.setSubChunkCount( chunk.getAvailableSubChunks() );
            BinaryStream binaryStream = new BinaryStream();
            chunk.writeTo( binaryStream );
            levelChunkPacket.setData( binaryStream.getBuffer() );
            this.sendPacket( levelChunkPacket );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        long hash = Utils.toLong( chunk.getChunkX(), chunk.getChunkZ() );
        this.loadedChunks.add( hash );
        this.loadingChunks.remove( hash );
    }

    public void needNewChunks( boolean forceResendEntities ) {
        try {
            int currentXChunk = Utils.blockToChunk( (int) this.player.getX() );
            int currentZChunk = Utils.blockToChunk( (int) this.player.getZ() );

            int viewDistance = this.player.getViewDistance();

            List<Pair<Integer, Integer>> toSendChunks = new ArrayList<>();

            for ( int sendXChunk = -viewDistance; sendXChunk <= viewDistance; sendXChunk++ ) {
                for ( int sendZChunk = -viewDistance; sendZChunk <= viewDistance; sendZChunk++ ) {
                    double distance = Math.sqrt( sendZChunk * sendZChunk + sendXChunk * sendXChunk );
                    long chunkDistance = Math.round( distance );

                    if ( chunkDistance <= viewDistance ) {
                        Pair<Integer, Integer> newChunk = new Pair<>( currentXChunk + sendXChunk, currentZChunk + sendZChunk );

                        if ( forceResendEntities ) {
                            toSendChunks.add( newChunk );
                        } else {
                            long hash = Utils.toLong( newChunk.getFirst(), newChunk.getSecond() );
                            if ( !this.loadedChunks.contains( hash ) && !this.loadingChunks.contains( hash ) ) {
                                toSendChunks.add( newChunk );
                            }
                        }
                    }
                }
            }

            toSendChunks.sort( ( chunkX, chunkZ ) -> {
                if ( Objects.equals( chunkX.getFirst(), chunkZ.getFirst() ) && Objects.equals( chunkX.getSecond(), chunkZ.getSecond() ) ) {
                    return 0;
                }

                int distXFirst = Math.abs( chunkX.getFirst() - currentXChunk );
                int distXSecond = Math.abs( chunkZ.getFirst() - currentXChunk );

                int distZFirst = Math.abs( chunkX.getSecond() - currentZChunk );
                int distZSecond = Math.abs( chunkZ.getSecond() - currentZChunk );

                if ( distXFirst + distZFirst > distXSecond + distZSecond ) {
                    return 1;
                } else if ( distXFirst + distZFirst < distXSecond + distZSecond ) {
                    return -1;
                }

                return 0;
            } );

            for ( Pair<Integer, Integer> chunk : toSendChunks ) {
                long hash = Utils.toLong( chunk.getFirst(), chunk.getSecond() );
                if ( forceResendEntities ) {
                    if ( !this.loadedChunks.contains( hash ) && !this.loadingChunks.contains( hash ) ) {
                        this.loadingChunks.add( hash );
                        this.requestChunk( chunk.getFirst(), chunk.getSecond() );
                    }
                } else {
                    this.loadingChunks.add( hash );
                    this.requestChunk( chunk.getFirst(), chunk.getSecond() );
                }
            }

            boolean unloaded = false;

            for ( long hash : this.loadedChunks ) {
                int x = (int) ( hash >> 32 );
                int z = (int) ( hash ) + Integer.MIN_VALUE;

                if ( Math.abs( x - currentXChunk ) > viewDistance || Math.abs( z - currentZChunk ) > viewDistance ) {
                    unloaded = true;
                    this.loadedChunks.remove( hash );
                }
            }

            for ( long hash : this.loadingChunks ) {
                int x = (int) ( hash >> 32 );
                int z = (int) ( hash ) + Integer.MIN_VALUE;

                if ( Math.abs( x - currentXChunk ) > viewDistance || Math.abs( z - currentZChunk ) > viewDistance ) {
                    this.loadingChunks.remove( hash );
                }
            }

            if ( unloaded || !this.chunkSendQueue.isEmpty() ) {
                this.sendNetworkChunkPublisher();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void requestChunk( int chunkX, int chunkZ ) {
        final Chunk chunk = this.player.getLocation().getWorld().getChunk( chunkX, chunkZ );
        if ( chunk != null ) {
            this.chunkSendQueue.offer( chunk );
        } else {
            System.out.println( "Chunk is null" );
        }
    }

    public boolean knowsChunk( int chunkX, int chunkZ ) {
        return this.loadedChunks.contains( Utils.toLong( chunkX, chunkZ ) );
    }

    public void sendPacket( Packet packet ) {
        this.sendPacket( packet, false );
    }

    public void sendPacket( Packet packet, boolean direct ) {
        if ( !direct && this.player.isSpawned() ) {
            this.sendQueue.offer( packet );
            return;
        }

        this.batchPackets( Collections.singletonList( packet ), true );
    }

    public void batchPackets( List<Packet> packets, boolean direct ) {
        BatchPacket batchPacket = new BatchPacket();
        for ( Packet packet : packets ) {
            batchPacket.addPacket( packet );
        }
        batchPacket.write();

        EncapsulatedPacket encapsulatedPacket = new EncapsulatedPacket();
        encapsulatedPacket.reliability = 0;
        encapsulatedPacket.buffer = batchPacket.getBuffer();

        this.connection.addEncapsulatedToQueue( encapsulatedPacket, direct ? Connection.Priority.IMMEDIATE : Connection.Priority.NORMAL );
    }

    public void sendStatus( PlayStatusPacket.Status status ) {
        PlayStatusPacket playStatusPacket = new PlayStatusPacket();
        playStatusPacket.setStatus( status );
        this.sendPacket( playStatusPacket );
    }

    public void sendResourcePackInfo() {
        ResourcePacksInfoPacket resourcePacksInfoPacket = new ResourcePacksInfoPacket();
        resourcePacksInfoPacket.setScripting( false );
        resourcePacksInfoPacket.setForceAccept( false );
        this.sendPacket( resourcePacksInfoPacket );
    }

    public void sendResourcePackStack() {
        ResourcePackStackPacket resourcePackStackPacket = new ResourcePackStackPacket();
        resourcePackStackPacket.setMustAccept( false );
        this.sendPacket( resourcePackStackPacket );
    }

    public void setViewDistance( int distance ) {
        ChunkRadiusUpdatedPacket chunkRadiusUpdatedPacket = new ChunkRadiusUpdatedPacket();
        chunkRadiusUpdatedPacket.setChunkRadius( distance );
        this.sendPacket( chunkRadiusUpdatedPacket );
    }

    public void sendNetworkChunkPublisher() {
        NetworkChunkPublisherUpdatePacket publisherUpdatePacket = new NetworkChunkPublisherUpdatePacket();
        publisherUpdatePacket.setX( (int) Math.floor( this.player.getLocation().getX() ) );
        publisherUpdatePacket.setY( (int) Math.floor( this.player.getLocation().getY() ) );
        publisherUpdatePacket.setZ( (int) Math.floor( this.player.getLocation().getZ() ) );
        publisherUpdatePacket.setRadius( this.player.getViewDistance() << 4 );
        this.sendPacket( publisherUpdatePacket );
    }

    public void disconnect( String message ) {
        DisconnectPacket disconnectPacket = new DisconnectPacket();
        disconnectPacket.setMessage( message );
        this.connection.disconnect( message );
        this.sendPacket( disconnectPacket );
    }

    public void movePlayer( Player player, PlayerMovePacket.Mode mode ) {
        PlayerMovePacket playerMovePacket = new PlayerMovePacket();
        playerMovePacket.setEntityRuntimeId( player.getEntityId() );
        playerMovePacket.setX( player.getLocation().getX() );
        playerMovePacket.setY( player.getLocation().getY() + player.getEyeHeight() );
        playerMovePacket.setZ( player.getLocation().getZ() );
        playerMovePacket.setYaw( player.getLocation().getYaw() );
        playerMovePacket.setPitch( player.getLocation().getPitch() );
        playerMovePacket.setHeadYaw( player.getHeadYaw() );
        playerMovePacket.setMode( mode );
        playerMovePacket.setOnGround( player.isOnGround() );
        playerMovePacket.setRidingEntityId( 0 );
        playerMovePacket.setTick( 0 );
        this.sendPacket( playerMovePacket );
    }

    public void sendTime( int time ) {
        SetTimePacket setTimePacket = new SetTimePacket();
        setTimePacket.setTime( time );
        this.sendPacket( setTimePacket );
    }

    public void sendMessage( String message, TextPacket.Type type ) {
        TextPacket textPacket = new TextPacket();
        textPacket.setType( type );
        textPacket.setMessage( message );
        textPacket.setLocalized( false );
        textPacket.setXuid( this.player.getXuid() );
        textPacket.setDeviceId( this.player.getDeviceInfo().getDeviceId() );
        this.sendPacket( textPacket );
    }

    public void sendAttributes( List<Attribute> attributes ) {
        UpdateAttributesPacket attributesPacket = new UpdateAttributesPacket();
        attributesPacket.setEntityId( this.player.getEntityId() );
        attributesPacket.setAttributes( attributes );
        attributesPacket.setTick( 0 ); //TODO
        this.sendPacket( attributesPacket );
    }

    public void sendattribute( Attribute attribute ) {
        UpdateAttributesPacket attributesPacket = new UpdateAttributesPacket();
        attributesPacket.setEntityId( this.player.getEntityId() );
        attributesPacket.setAttributes( Collections.singletonList( attribute ) );
        attributesPacket.setTick( 0 ); //TODO
        this.sendPacket( attributesPacket );
    }

    public void sendMetadata() {
        SetEntityDataPacket setEntityDataPacket = new SetEntityDataPacket();
        setEntityDataPacket.setEntityId( this.player.getEntityId() );
        setEntityDataPacket.setMetadata( this.player.getMetadata() );
        setEntityDataPacket.setTick( 0 );
        this.sendPacket( setEntityDataPacket );
    }

    public void sendAdventureSettings() {
        GameMode gameMode = this.player.getGameMode();

        AdventureSettings adventureSettings = this.player.getAdventureSettings();
        adventureSettings.setWorldImmutable( gameMode.ordinal() == 3 );
        adventureSettings.setCanFly( gameMode.ordinal() > 0 );
        adventureSettings.setNoClip( gameMode.ordinal() == 3 );
        adventureSettings.setFlying( gameMode.ordinal() == 3 );
        adventureSettings.setAttackMobs( gameMode.ordinal() < 2 );
        adventureSettings.setAttackPlayers( gameMode.ordinal() < 2 );
        adventureSettings.setNoPvP( gameMode.ordinal() == 3 );
        adventureSettings.update();
    }

    public void playSound( Vector position, Sound sound, float volume, float pitch ) {
        PlaySoundPacket playSoundPacket = new PlaySoundPacket();
        playSoundPacket.setSound( sound );
        playSoundPacket.setPosition( position );
        playSoundPacket.setVolume( volume );
        playSoundPacket.setPitch( pitch );
        this.sendPacket( playSoundPacket );
    }

    public void spawnPlayer( Player player ) {
        AddPlayerPacket addPlayerPacket = new AddPlayerPacket();
        addPlayerPacket.setUuid( player.getUUID() );
        addPlayerPacket.setName( player.getName() );
        addPlayerPacket.setEntityId( player.getEntityId() );
        addPlayerPacket.setRuntimeEntityId( player.getEntityId() );
        addPlayerPacket.setPlatformChatId( player.getDeviceInfo().getDeviceId() );
        addPlayerPacket.setX( player.getX() );
        addPlayerPacket.setY( player.getY() );
        addPlayerPacket.setZ( player.getZ() );
        addPlayerPacket.setVelocity( new Vector( 0, 0, 0 ) );
        addPlayerPacket.setPitch( player.getPitch() );
        addPlayerPacket.setHeadYaw( player.getHeadYaw() );
        addPlayerPacket.setYaw( player.getYaw() );
        addPlayerPacket.setItem( ItemType.AIR.getItem() );
        addPlayerPacket.setMetadata( player.getMetadata() );
        addPlayerPacket.setDeviceInfo( player.getDeviceInfo() );
        this.sendPacket( addPlayerPacket );
    }

    public void sendPlayerList() {
        PlayerListPacket playerListPacket = new PlayerListPacket();
        playerListPacket.setType( PlayerListPacket.Type.ADD );
        this.server.getPlayerListEntry().forEach( ( uuid, entry ) -> {
            if ( uuid != this.player.getUUID() ) {
                playerListPacket.getEntries().add( entry );
            }
        } );
        this.player.getPlayerConnection().sendPacket( playerListPacket );
    }

    public void addPlayerToList() {
        PlayerListPacket playerListPacket = new PlayerListPacket();
        playerListPacket.setType( PlayerListPacket.Type.ADD );
        PlayerListPacket.Entry playerListEntry = new PlayerListPacket.Entry(
                this.player.getUUID(),
                this.player.getEntityId(),
                this.player.getName(),
                this.player.getDeviceInfo(),
                this.player.getXuid(),
                this.player.getSkin()
        );
        playerListPacket.getEntries().add( playerListEntry );
        this.server.getPlayerListEntry().put( this.player.getUUID(), playerListEntry );
        this.server.broadcastPacket( playerListPacket );
    }

    public void removeFromList() {
        PlayerListPacket playerListPacket = new PlayerListPacket();
        playerListPacket.setType( PlayerListPacket.Type.REMOVE );
        playerListPacket.getEntries().add( new PlayerListPacket.Entry( this.player.getUUID() ) );
        this.server.getPlayerListEntry().remove( this.player.getUUID() );
        this.server.broadcastPacket( playerListPacket );
    }

    public void despawnEntity( Entity entity ) {
        RemoveEntityPacket removeEntityPacket = new RemoveEntityPacket();
        removeEntityPacket.setEntityId( entity.getEntityId() );
        this.sendPacket( removeEntityPacket );
    }

    public void sendUpdateBlock( BlockPosition blockPosition ) {
        Block block = this.player.getWorld().getBlock( blockPosition );

        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setBlockId( block.getRuntimeId() );
        updateBlockPacket.setPosition( blockPosition );
        updateBlockPacket.setFlags( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        updateBlockPacket.setLayer( block.getLayer() );
        this.sendPacket( updateBlockPacket );
    }

    public void joinGame() {
        this.player.setSpawned( true );
        this.sendNetworkChunkPublisher();

        this.sendTime( 1000 );
        this.sendAdventureSettings();
        this.sendAttributes( this.player.getAttributes().getAttributes() );

        this.sendStatus( PlayStatusPacket.Status.PLAYER_SPAWN );
        this.sendMetadata();

        this.player.getInventory().addViewer( this.player );
        this.player.getCursorInventory().addViewer( this.player );

        this.addPlayerToList();
        if ( this.server.getOnlinePlayers().size() > 1 ) {
            this.sendPlayerList();
        }

        this.player.getChunk().addEntity( this.player );

        this.player.getInventory().setItem( 0, new ItemFurnace() );
    }

    public void leaveGame() {
        this.player.getWorld().removePlayer( this.player );
        this.player.getChunk().removeEntity( this.player );

        this.player.getInventory().removeViewer( this.player );
        this.player.getCursorInventory().removeViewer( this.player );

        this.removeFromList();
        for ( Player onlinePlayer : this.server.getOnlinePlayers() ) {
            onlinePlayer.getPlayerConnection().despawnEntity( this.player );
        }

        this.server.removePlayer( this.player.getAddress() );
        this.server.setOnlinePlayers( this.server.getOnlinePlayers().size() );
        this.server.broadcastMessage( "§e" + this.player.getName() + " left the game" );
    }

    public void openInventory( Inventory inventory, BlockPosition position ) {
        ContainerOpenPacket containerOpenPacket = new ContainerOpenPacket();
        containerOpenPacket.setEntityId( this.player.getEntityId() );
        containerOpenPacket.setWindowType( inventory.getWindowType() );
        containerOpenPacket.setWindowId( WindowId.OPEN_CONTAINER );
        containerOpenPacket.setPosition( position );
        this.sendPacket( containerOpenPacket );
    }

    public void closeInventory( int windowId, boolean isServerSide ) {
        ContainerClosePacket containerClosePacket = new ContainerClosePacket();
        containerClosePacket.setWindowId( windowId );
        containerClosePacket.setServerInitiated( isServerSide );
        this.sendPacket( containerClosePacket );
    }

    //TEST
    public boolean canInteract( Vector position, int maxDistance ) {
        // Distance
        Vector eyePosition = this.player.getLocation().add( 0, this.player.getEyeHeight(), 0 );
        if ( eyePosition.distanceSquared( position ) > Utils.square( maxDistance ) ) {
            return false;
        }

        // Direction
        Vector playerPosition = this.player.getLocation();
        Vector2 directionPlane = this.getDirectionPlane();
        float dot = directionPlane.dot( new Vector2( eyePosition.getX(), eyePosition.getZ() ) );
        float dot1 = directionPlane.dot( new Vector2( playerPosition.getX(), playerPosition.getZ() ) );
        return ( dot1 - dot ) >= -( Math.sqrt( 3 ) / 2 );
    }

    private Vector2 getDirectionPlane() {
        return ( new Vector2( (float) ( -Math.cos( Math.toRadians( this.player.getYaw() ) - Math.PI / 2 ) ), (float) ( -Math.sin( Math.toRadians( this.player.getYaw() ) - Math.PI / 2 ) ) ) ).normalize();
    }
}
