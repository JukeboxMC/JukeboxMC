package org.jukeboxmc.world.generator;

import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class WorldGenerator {

    public abstract void generate( Chunk chunk );

    public abstract Vector getSpawnLocation();
}
