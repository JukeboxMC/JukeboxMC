package org.jukeboxmc.world.generator;

import org.jukeboxmc.math.Vector;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EmptyGenerator extends WorldGenerator {

    @Override
    public void generate( int chunkX, int chunkZ ) {

    }

    @Override
    public void populate( int chunkX, int chunkZ ) {

    }

    @Override
    public Vector getSpawnLocation() {
        return new Vector( 0, 64, 0 );
    }

}
