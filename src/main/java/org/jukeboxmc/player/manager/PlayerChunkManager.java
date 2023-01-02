package org.jukeboxmc.player.manager;

import com.nukkitx.protocol.bedrock.packet.ChunkRadiusUpdatedPacket;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
import com.nukkitx.protocol.bedrock.packet.NetworkChunkPublisherUpdatePacket;
import it.unimi.dsi.fastutil.longs.*;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.jukeboxmc.Server;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.ChunkComparator;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongConsumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerChunkManager {

    private final Player player;
    private final ChunkComparator comparator;
    private final LongSet loadedChunks = new LongOpenHashSet();
    private final Long2ObjectMap<LevelChunkPacket> sendQueue = new Long2ObjectOpenHashMap<>();
    private final AtomicLong chunksSentCounter = new AtomicLong();
    private final LongConsumer removeChunkLoader;
    private volatile int radius;

    public PlayerChunkManager( Player player ) {
        this.player = player;
        this.comparator = new ChunkComparator( player );
        this.removeChunkLoader = chunkKey -> {
            Chunk chunk = this.player.getWorld().getLoadedChunk( chunkKey, player.getDimension() );
            if ( chunk != null ) {
                chunk.removeLoader( this.player );
            }
        };
    }

    public synchronized void sendQueued() {
        int chunksPerTick = 4; //this.player.getServer().getConfig("chunk-sending.per-tick", 4);
        ObjectIterator<Long2ObjectMap.Entry<LevelChunkPacket>> sendQueueIterator = this.sendQueue.long2ObjectEntrySet().iterator();
        // Remove chunks which are out of range
        while ( sendQueueIterator.hasNext() ) {
            Long2ObjectMap.Entry<LevelChunkPacket> entry = sendQueueIterator.next();
            long key = entry.getLongKey();
            if ( !this.loadedChunks.contains( key ) ) {
                sendQueueIterator.remove();

                Chunk chunk = this.player.getWorld().getLoadedChunk( key, this.player.getDimension() );
                if ( chunk != null ) {
                    chunk.removeLoader( this.player );
                }
            }
        }

        LongList list = new LongArrayList( this.sendQueue.keySet() );
        list.unstableSort( this.comparator );

        for ( long key : list.toLongArray() ) {
            if ( chunksPerTick < 0 ) {
                break;
            }

            LevelChunkPacket packet = this.sendQueue.get( key );
            if ( packet == null ) {
                // Next packet is not available.
                break;
            }

            this.sendQueue.remove( key );
            this.player.getPlayerConnection().sendPacket( packet );

            Chunk chunk = this.player.getWorld().getLoadedChunk( key, this.player.getDimension() );
            if ( chunk != null ) {
                for ( Entity entity : chunk.getEntities() ) {
                    if ( !( entity instanceof Player ) && !entity.isClosed() ) {
                        entity.spawn( this.player );
                    }
                }
            }

            chunksPerTick--;
            this.chunksSentCounter.incrementAndGet();
        }

    }

    public void queueNewChunks() {
        this.queueNewChunks( this.player.getLocation() );
    }

    public void queueNewChunks( Vector pos ) {
        this.queueNewChunks( pos.getBlockX() >> 4, pos.getBlockZ() >> 4 );
    }

    public synchronized void queueNewChunks( int chunkX, int chunkZ ) {
        int radius = this.getChunkRadius();
        int radiusSqr = radius * radius;

        LongSet chunksForRadius = new LongOpenHashSet();

        LongSet sentCopy = new LongOpenHashSet( this.loadedChunks );

        LongList chunksToLoad = new LongArrayList();

        for ( int x = -radius; x <= radius; ++x ) {
            for ( int z = -radius; z <= radius; ++z ) {
                // Chunk radius is circular so we need to remove the corners.
                if ( ( x * x ) + ( z * z ) > radiusSqr ) {
                    continue;
                }

                int cx = chunkX + x;
                int cz = chunkZ + z;

                final long key = Utils.toLong( cx, cz );

                chunksForRadius.add( key );
                if ( this.loadedChunks.add( key ) ) {
                    chunksToLoad.add( key );
                }
            }
        }

        boolean loadedChunksChanged = this.loadedChunks.retainAll( chunksForRadius );
        if ( loadedChunksChanged || !chunksToLoad.isEmpty() ) {
            NetworkChunkPublisherUpdatePacket packet = new NetworkChunkPublisherUpdatePacket();
            packet.setPosition( this.player.getLocation().toVector3i() );
            packet.setRadius( this.radius );
            this.player.getPlayerConnection().sendPacket( packet );
        }

        // Order chunks for smoother loading
        chunksToLoad.sort( this.comparator );

        for ( final long key : chunksToLoad.toLongArray() ) {
            final int cx = Utils.fromHashX( key );
            final int cz = Utils.fromHashZ( key );

            if ( this.sendQueue.putIfAbsent( key, null ) == null ) {
                this.player.getWorld().getChunkFuture( cx, cz, this.player.getDimension() ).thenApply( chunk -> {
                            chunk.addLoader( this.player );
                            return chunk;
                        } ).thenApplyAsync( Chunk::createLevelChunkPacket, Server.getInstance().getScheduler().getChunkExecutor() )
                        .whenComplete( ( packet, throwable ) -> {
                            synchronized (PlayerChunkManager.this) {
                                if ( throwable != null ) {
                                    if ( this.sendQueue.remove( key, null ) ) {
                                        this.loadedChunks.remove( key );
                                    }
                                } else if ( !this.sendQueue.replace( key, null, packet ) ) {
                                    // The chunk was already loaded!?
                                    if ( this.sendQueue.containsKey( key ) ) {
                                        Server.getInstance().getLogger().warn( "Chunk (" + cx + "," + cz + ") already loaded for "
                                                + this.player.getName() + ", values " + this.sendQueue.get( key ) );

                                    }
//                                    packet.release();
                                }
                            }
                        } );
            }
        }

        sentCopy.removeAll( chunksForRadius );
        // Remove player from chunk loaders
        sentCopy.forEach( this.removeChunkLoader );
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius( int radius ) {
        if ( this.radius != radius ) {
            this.radius = radius;
            ChunkRadiusUpdatedPacket chunkRadiusUpdatePacket = new ChunkRadiusUpdatedPacket();
            chunkRadiusUpdatePacket.setRadius( radius >> 4 );
            this.player.getPlayerConnection().sendPacket( chunkRadiusUpdatePacket );
            this.queueNewChunks();
        }
    }

    public int getChunkRadius() {
        return this.radius >> 4;
    }

    public void setChunkRadius( int chunkRadius ) {
        chunkRadius = Utils.clamp( chunkRadius, 8, this.player.getServer().getViewDistance() );
        this.setRadius( chunkRadius << 4 );
    }

    public boolean isChunkInView( int x, int z ) {
        return this.isChunkInView( Utils.toLong( x, z ) );
    }

    public synchronized boolean isChunkInView( long key ) {
        return this.loadedChunks.contains( key );
    }

    public long getChunksSent() {
        return chunksSentCounter.get();
    }

    public LongSet getLoadedChunks() {
        return LongSets.unmodifiable( this.loadedChunks );
    }

    public synchronized void resendChunk( int chunkX, int chunkZ ) {
        long chunkKey = Utils.toLong( chunkX, chunkZ );
        this.loadedChunks.remove( chunkKey );
        this.removeChunkLoader.accept( chunkKey );
    }

    public void prepareRegion( Vector pos ) {
        this.prepareRegion( pos.getChunkX(), pos.getChunkZ() );
    }

    public void prepareRegion( int chunkX, int chunkZ ) {
        this.clear();
        this.queueNewChunks( chunkX, chunkZ );
    }

    public synchronized void clear() {
        this.sendQueue.clear();

        this.loadedChunks.forEach( this.removeChunkLoader );
        this.loadedChunks.clear();
    }
}
