package org.jukeboxmc.world.generator;

import org.jukeboxmc.math.Vector;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EmptyGenerator extends Generator {

    @Override
    public void generate( int x, int z ) {

    }

    @Override
    public void populate( int x, int z ) {

    }

    @Override
    public Vector getSpawnLocation() {
        return new Vector( 0, 64, 0 );
    }
}
