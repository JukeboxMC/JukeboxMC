package org.jukeboxmc.world.generator;

import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EmptyGenerator extends WorldGenerator {

    @Override
    public void generate( Chunk chunk ) {

    }

    @Override
    public Vector getSpawnLocation() {
        return new Vector( 0, 64, 0 );
    }
}
