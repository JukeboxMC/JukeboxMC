package org.jukeboxmc.player;

import org.jukeboxmc.Server;
import org.jukeboxmc.entity.adventure.AdventureSettings;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.event.player.PlayerQuitEvent;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.logger.Logger;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.math.Vector2;
import org.jukeboxmc.network.handler.PacketHandler;
import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.network.raknet.Connection;
import org.jukeboxmc.network.raknet.protocol.EncapsulatedPacket;
import org.jukeboxmc.utils.ChunkComparator;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Sound;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerConnection {

    private Player player;
    private Server server;
    private Logger logger;
    private Connection connection;

    private ChunkComparator chunkComparator;

    private Queue<Packet> incomingQueue = new ConcurrentLinkedQueue<>();
    private Queue<Long> chunkLoadQueue = new ConcurrentLinkedQueue<>();

    private Queue<Chunk> chunkQueue = new ConcurrentLinkedQueue<>();
    private List<Long> loadingChunks = new CopyOnWriteArrayList<>();
    private List<Long> loadedChunks = new CopyOnWriteArrayList<>();

    public PlayerConnection( Player player, Server server, Connection connection ) {
        this.player = player;
        this.server = server;
        this.logger = server.getLogger();
        this.connection = connection;
        this.chunkComparator = new ChunkComparator( player );
    }

    public void update( long currentTick ) {
        if ( !this.chunkQueue.isEmpty() ) {
            Chunk chunk;
            while ( ( chunk = this.chunkQueue.poll() ) != null ) {
                this.sendChunk( chunk );
            }
        }

        if ( !this.chunkLoadQueue.isEmpty() ) {
            Long hash;
            while ( ( hash = this.chunkLoadQueue.poll() ) != null ) {
                int chunkX = Utils.fromHashX( hash );
                int chunkZ = Utils.fromHashZ( hash );

                World world = this.player.getWorld();
                world.loadChunk( chunkX, chunkZ ).whenComplete( ( chunk, throwable ) -> {
                    this.chunkQueue.offer( chunk );
                } );
            }
        }
        this.needNewChunks();
    }

    public void updateNetwork( long currentTick ) {
        if ( !this.incomingQueue.isEmpty() ) {
            try {
                Packet packet;
                while ( ( packet = this.incomingQueue.poll() ) != null ) {
                    PacketHandler handler = PacketRegistry.getHandler( packet.getClass() );
                    if ( handler != null ) {
                        handler.handle( packet, player );
                    } else {
                        this.logger.debug( "Handler for packet: " + packet.getClass().getSimpleName() + " is missing" );
                    }
                }
            } catch ( Throwable throwable ) {
                throwable.printStackTrace();
            }
        }
    }

    public void sendChunk( Chunk chunk ) {
        this.sendPacket( chunk.createLevelChunkPacket() );

        long hash = Utils.toLong( chunk.getChunkX(), chunk.getChunkZ() );
        this.loadedChunks.add( hash );
        this.loadingChunks.remove( hash );
    }

    public void needNewChunks() {
        try {
            int currentXChunk = Utils.blockToChunk( (int) this.player.getX() );
            int currentZChunk = Utils.blockToChunk( (int) this.player.getZ() );

            int viewDistance = this.player.getViewDistance();

            List<Long> toSendChunks = new ArrayList<>();

            for ( Long hash : PlayerChunkManager.forPlayer( currentXChunk, currentZChunk, viewDistance ) ) {
                if ( !this.loadedChunks.contains( hash ) && !this.loadingChunks.contains( hash ) ) {
                    toSendChunks.add( hash );
                }
            }

            toSendChunks.sort( this.chunkComparator );

            for ( long hash : toSendChunks ) {
                this.loadingChunks.add( hash );
                this.requestChunk( Utils.fromHashX( hash ), Utils.fromHashZ( hash ) );
            }

            boolean unloaded = false;

            for ( long hash : this.loadedChunks ) {
                int x = Utils.fromHashX( hash );
                int z = Utils.fromHashZ( hash );

                if ( Math.abs( x - currentXChunk ) > viewDistance || Math.abs( z - currentZChunk ) > viewDistance ) {
                    unloaded = true;
                    this.loadedChunks.remove( hash );
                }
            }

            for ( long hash : this.loadingChunks ) {
                int x = Utils.fromHashX( hash );
                int z = Utils.fromHashZ( hash );

                if ( Math.abs( x - currentXChunk ) > viewDistance || Math.abs( z - currentZChunk ) > viewDistance ) {
                    this.loadingChunks.remove( hash );
                }
            }

            if ( unloaded || !this.chunkQueue.isEmpty() ) {
                this.sendNetworkChunkPublisher();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void resetChunks() {
        this.chunkLoadQueue.clear();
        this.loadedChunks.clear();
        this.loadingChunks.clear();
    }

    public Queue<Long> getChunkLoadQueue() {
        return this.chunkLoadQueue;
    }

    public List<Long> getLoadedChunks() {
        return this.loadedChunks;
    }

    public List<Long> getLoadingChunks() {
        return this.loadingChunks;
    }

    public void handlePacketSync( Packet packet ) {
        this.incomingQueue.offer( packet );
    }

    public void requestChunk( int chunkX, int chunkZ ) {
        this.chunkLoadQueue.offer( Utils.toLong( chunkX, chunkZ ) );
    }

    public boolean knowsChunk( int chunkX, int chunkZ ) {
        return this.loadedChunks.contains( Utils.toLong( chunkX, chunkZ ) );
    }

    public void sendPacket( Packet packet ) {
        this.sendPacket( packet, false );
    }

    public void sendPacket( Packet packet, boolean direct ) {
        BatchPacket batchPacket = new BatchPacket();
        batchPacket.addPacket( packet );
        batchPacket.write();

        EncapsulatedPacket encapsulatedPacket = new EncapsulatedPacket();
        encapsulatedPacket.reliability = 0;
        encapsulatedPacket.buffer = batchPacket.getBuffer();

        this.connection.addEncapsulatedToQueue( encapsulatedPacket, direct ? Connection.Priority.IMMEDIATE : Connection.Priority.NORMAL );
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
        disconnectPacket.setHideDisconnectScreen( false );
        disconnectPacket.setMessage( message );
        this.connection.disconnect( message );
        this.sendPacket( disconnectPacket );
    }

    public void movePlayer( Vector vector, PlayerMovePacket.Mode mode ) {
        PlayerMovePacket playerMovePacket = new PlayerMovePacket();
        playerMovePacket.setEntityRuntimeId( this.player.getEntityId() );
        playerMovePacket.setX( vector.getX() );
        playerMovePacket.setY( vector.getY() + player.getEyeHeight()  );
        playerMovePacket.setZ( vector.getZ() );
        playerMovePacket.setYaw( this.player.getYaw() );
        playerMovePacket.setPitch( this.player.getPitch() );
        playerMovePacket.setHeadYaw( this.player.getYaw() );
        playerMovePacket.setMode( mode );
        playerMovePacket.setOnGround( this.player.isOnGround() );
        playerMovePacket.setRidingEntityId( 0 );
        playerMovePacket.setTick( 0 );
        this.sendPacket( playerMovePacket );
    }

    public void movePlayer( Location location, PlayerMovePacket.Mode mode ) {
        PlayerMovePacket playerMovePacket = new PlayerMovePacket();
        playerMovePacket.setEntityRuntimeId( this.player.getEntityId() );
        playerMovePacket.setX( location.getX() );
        playerMovePacket.setY( location.getY() + player.getEyeHeight() );
        playerMovePacket.setZ( location.getZ() );
        playerMovePacket.setYaw( location.getYaw() );
        playerMovePacket.setPitch( location.getPitch() );
        playerMovePacket.setHeadYaw( location.getYaw() );
        playerMovePacket.setMode( mode );
        playerMovePacket.setOnGround( this.player.isOnGround() );
        playerMovePacket.setRidingEntityId( 0 );
        playerMovePacket.setTick( 0 );
        this.sendPacket( playerMovePacket );
    }

    public void movePlayer( Player player, PlayerMovePacket.Mode mode ) {
        PlayerMovePacket playerMovePacket = new PlayerMovePacket();
        playerMovePacket.setEntityRuntimeId( player.getEntityId() );
        playerMovePacket.setX( player.getX() );
        playerMovePacket.setY( player.getY() + player.getEyeHeight() );
        playerMovePacket.setZ( player.getZ() );
        playerMovePacket.setYaw( player.getYaw() );
        playerMovePacket.setPitch( player.getPitch() );
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

    public void spawnPlayer( Player player ) {
        if ( this.player != player ) {
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
    }

    public void spawnToAll() {
        for ( Player players : this.player.getWorld().getPlayers() ) {
            if ( players != null ) {
                players.getPlayerConnection().spawnPlayer( this.player );
                this.player.getPlayerConnection().spawnPlayer( players );
            }
        }
    }

    public void despawn( Player player ) {
        RemoveEntityPacket removeEntityPacket = new RemoveEntityPacket();
        removeEntityPacket.setEntityId( this.player.getEntityId() );
        player.getPlayerConnection().sendPacket( removeEntityPacket );
    }

    public void despawnForAll() {
        for ( Player players : this.player.getWorld().getPlayers() ) {
            this.despawn( players );
            players.getPlayerConnection().despawn( this.player );
        }
    }

    public void joinGame() {
        World world = this.server.getDefaultWorld();
        world.addPlayer( this.player );

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

        this.player.setSpawned( true );
    }

    public void leaveGame( String reason ) {
        this.player.getWorld().removePlayer( this.player );
        this.player.getChunk().removeEntity( this.player );

        this.player.getInventory().removeViewer( this.player );
        this.player.getCursorInventory().removeViewer( this.player );

        this.removeFromList();
        this.despawnForAll();

        this.server.removePlayer( this.player.getAddress() );
        this.server.setOnlinePlayers( this.server.getOnlinePlayers().size() );

        PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent( this.player, "§e" + this.player.getName() + " left the game" );
        Server.getInstance().getPluginManager().callEvent( playerQuitEvent );
        if ( playerQuitEvent.getQuitMessage() != null && !playerQuitEvent.getQuitMessage().isEmpty() ) {
            this.server.broadcastMessage( playerQuitEvent.getQuitMessage() );
        }
        this.logger.info( this.player.getName() + " logged out reason: " + reason );
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
