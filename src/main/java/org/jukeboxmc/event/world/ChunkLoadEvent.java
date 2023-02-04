package org.jukeboxmc.event.world;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ChunkLoadEvent extends WorldEvent implements Cancellable {

    private final Chunk chunk;

    /**
     * Creates a new {@link WorldEvent}
     *
     * @param world which represents the world which comes with this event
     */
    public ChunkLoadEvent( World world, Chunk chunk ) {
        super( world );
        this.chunk = chunk;
    }

    public Chunk getChunk() {
        return this.chunk;
    }
}
