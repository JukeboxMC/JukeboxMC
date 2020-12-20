package org.jukeboxmc.player;

import org.jukeboxmc.entity.adventure.AdventureSettings;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.network.raknet.Connection;
import org.jukeboxmc.network.raknet.protocol.EncapsulatedPacket;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.utils.Pair;
import org.jukeboxmc.utils.Utils;
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
    private Connection connection;

    private Queue<Chunk> chunkSendQueue = new ConcurrentLinkedQueue<>();
    private Queue<Packet> sendQueue = new ConcurrentLinkedQueue<>();
    private Set<Long> loadingChunks = new CopyOnWriteArraySet<>();
    private Set<Long> loadedChunks = new CopyOnWriteArraySet<>();

    public PlayerConnection( Player player, Connection connection ) {
        this.player = player;
        this.connection = connection;
    }

    public void update( long timestamp ) {
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

    public void sendChunk( Chunk chunk ) {
        try {
            // System.out.println( "SendChunk" );
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


            toSendChunks.sort( ( o1, o2 ) -> {
                if ( Objects.equals( o1.getFirst(), o2.getFirst() ) && Objects.equals( o1.getSecond(), o2.getSecond() ) ) {
                    return 0;
                }

                int distXFirst = Math.abs( o1.getFirst() - currentXChunk );
                int distXSecond = Math.abs( o2.getFirst() - currentXChunk );

                int distZFirst = Math.abs( o1.getSecond() - currentZChunk );
                int distZSecond = Math.abs( o2.getSecond() - currentZChunk );

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
                    //    System.out.println( "RequestChunk-> " + chunk.getFirst() + ":" + chunk.getSecond() + " [" + this.chunkSendQueue.size() + "]" );
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

    private void requestChunk( int chunkX, int chunkZ ) {
        this.chunkSendQueue.offer( this.player.getLocation().getWorld().getChunk( chunkX, chunkZ ) );
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
        playerMovePacket.setY( player.getLocation().getY() );
        playerMovePacket.setZ( player.getLocation().getZ() );
        playerMovePacket.setYaw( player.getLocation().getYaw() );
        playerMovePacket.setPitch( player.getLocation().getPitch() );
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

    public boolean knowsChunk( int chunkX, int chunkZ ) {
        return this.loadedChunks.contains( Utils.toLong( chunkX, chunkZ ) );
    }
}
