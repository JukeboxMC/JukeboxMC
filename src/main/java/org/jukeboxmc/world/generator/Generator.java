package org.jukeboxmc.world.generator;

import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Generator {

    public abstract void generate( Chunk chunk, int chunkX, int chunkZ );

    public abstract void populate( PopulationChunkManager manager, int chunkX, int chunkZ );

    public abstract void finish( PopulationChunkManager manager, int chunkX, int chunkZ );

    public abstract Vector getSpawnLocation();
}
