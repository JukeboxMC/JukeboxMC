package org.jukeboxmc.world;

import org.jukeboxmc.JukeboxMC;
import org.jukeboxmc.block.*;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.LevelSoundEventPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.UpdateBlockPacket;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class World {

    private String name;

    private int currentTick;

    private Map<Long, Chunk> chunkMap = new HashMap<>();
    private Map<Long, CompletableFuture<Chunk>> chunkFutures = new HashMap<>();
    private Map<Long, Player> players = new HashMap<>();

    public World( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void update( long timestamp ) {
        for ( Player player : this.players.values() ) {
            if ( player != null && player.isSpawned() ) {
                player.getPlayerConnection().update( timestamp );

                if ( this.currentTick % ( 20 * 5 * 60 ) == 0 ) {
                    player.getPlayerConnection().sendTime( this.currentTick );
                }
            }
        }
        this.currentTick++;
    }

    public void addPlayer( Player player ) {
        this.players.put( player.getEntityId(), player );
    }

    public void removePlayer( Player player ) {
        this.players.remove( player.getEntityId() );
    }

    public Collection<Player> getPlayers() {
        return this.players.values();
    }

    public void loadChunk( Chunk chunk ) {
        //TODO Chunk net gefunden in leveldb = generieren
    }

    public Chunk getChunk( int chunkX, int chunkZ ) {
        Long chunkHash = Utils.toLong( chunkX, chunkZ );
        if ( !this.chunkMap.containsKey( chunkHash ) ) {
            Chunk chunk = new Chunk( chunkX, chunkZ );
            for ( int blockX = 0; blockX < 16; blockX++ ) {
                for ( int blockZ = 0; blockZ < 16; blockZ++ ) {
                    chunk.setBlock( blockX, 0, blockZ, 0, BlockType.BEDROCK.getBlock() );
                    chunk.setBlock( blockX, 1, blockZ, 0, BlockType.DIRT.getBlock() );
                    chunk.setBlock( blockX, 2, blockZ, 0, BlockType.DIRT.getBlock() );
                    chunk.setBlock( blockX, 3, blockZ, 0, BlockType.DIRT.<BlockDirt>getBlock().setDirtType( BlockDirt.DirtType.COARSE ) );
                    chunk.setBlock( blockX, 4, blockZ, 0, BlockType.GRASS.getBlock() );
                    if ( new Random().nextInt( 5 ) == 1 ) {
                        chunk.setBlock( blockX, 5, blockZ, 0, BlockType.TALL_GRASS.getBlock());
                    }
                }
            }
            this.chunkMap.put( chunkHash, chunk ); //TODO this.loadChunk;
            return chunk;
        }
        return this.chunkMap.get( chunkHash );
    }

    public Block getBlock( Vector location, int layer ) {
        Chunk chunk = this.getChunk( location.getFloorX() >> 4, location.getFloorZ() >> 4 );
        return chunk.getBlock( location.getFloorX(), location.getFloorY(), location.getFloorX(), layer );
    }

    public void setBlock( Vector location, Block block ) {
        this.setBlock( location, block, 0 );
    }

    public void setBlock( Vector location, Block block, int layer ) {
        Chunk chunk = this.getChunk( location.getFloorX() >> 4, location.getFloorZ() >> 4 );
        chunk.setBlock( location.getFloorX(), location.getFloorY(), location.getFloorX() & 15, layer, block );

        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setPosition( location );
        updateBlockPacket.setBlockId( block.getRuntimeId() );
        updateBlockPacket.setFlags( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        updateBlockPacket.setLayer( layer );
        for ( Player player : this.getPlayers() ) {
            player.getPlayerConnection().sendPacket( updateBlockPacket );
        }
    }

    public void playSound( Player player, LevelSound levelSound ) {
        this.playSound( player, player.getLocation(), levelSound, ":", false, false );
    }

    public void playSound( Vector position, LevelSound levelSound ) {
        this.playSound( null, position, levelSound, ":", false, false );
    }

    public void playSound( Vector position, LevelSound levelSound, String entityIdentifier ) {
        this.playSound( null, position, levelSound, entityIdentifier, false, false );
    }

    public void playSound( Vector position, LevelSound levelSound, String entityIdentifier, boolean isBaby, boolean isGlobal ) {
        this.playSound( null, position, levelSound, entityIdentifier, isBaby, isGlobal );
    }

    public void playSound( Player player, Vector position, LevelSound levelSound, String entityIdentifier, boolean isBaby, boolean isGlobal ) {
        LevelSoundEventPacket levelSoundEventPacket = new LevelSoundEventPacket();
        levelSoundEventPacket.setLevelSound( levelSound );
        levelSoundEventPacket.setPosition( position );
        levelSoundEventPacket.setExtraData( -1 );
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

    public int getCurrentTick() {
        return this.currentTick;
    }
}
