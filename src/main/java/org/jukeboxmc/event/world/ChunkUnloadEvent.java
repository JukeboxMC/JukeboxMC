package org.jukeboxmc.event.world;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ChunkUnloadEvent extends WorldEvent implements Cancellable {

    private final Chunk chunk;
    private boolean saveChunk;

    /**
     * Creates a new {@link WorldEvent}
     *
     * @param world which represents the world which comes with this event
     */
    public ChunkUnloadEvent( World world, Chunk chunk, boolean saveChunk ) {
        super( world );
        this.chunk = chunk;
        this.saveChunk = saveChunk;
    }

    public Chunk getChunk() {
        return this.chunk;
    }

    public boolean isSaveChunk() {
        return this.saveChunk;
    }

    public void setSaveChunk( boolean saveChunk ) {
        this.saveChunk = saveChunk;
    }
}
