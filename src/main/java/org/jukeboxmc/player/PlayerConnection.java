package org.jukeboxmc.player;

import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.network.raknet.Connection;
import org.jukeboxmc.network.raknet.protocol.EncapsulatedPacket;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerConnection {

    private Player player;
    private Connection connection;

    public PlayerConnection( Player player, Connection connection ) {
        this.player = player;
        this.connection = connection;
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
    }

    public void disconnect( String message ) {
        DisconnectPacket disconnectPacket = new DisconnectPacket();
        disconnectPacket.setMessage( message );
        this.sendPacket( disconnectPacket );
        this.connection.disconnect( message );
    }

    public void movePlayer( Player player, PlayerMovePacket.Mode mode ) {
        PlayerMovePacket playerMovePacket = new PlayerMovePacket();
        playerMovePacket.setEntityRuntimeId( player.getEntityId() );
        playerMovePacket.setX( player.getLocation().getX() );
        playerMovePacket.setY( player.getLocation().getY() );
        playerMovePacket.setZ( player.getLocation().getZ() );
        playerMovePacket.setYaw( player.getLocation().getYaw() );
        playerMovePacket.setPitch( player.getLocation().getPitch() );
        playerMovePacket.setMode( mode );
        playerMovePacket.setOnGround( player.isOnGround() );
        playerMovePacket.setRidingEntityId( 0 );
        playerMovePacket.setTick( 0 );
        player.getPlayerConnection().sendPacket( playerMovePacket );
    }
}
