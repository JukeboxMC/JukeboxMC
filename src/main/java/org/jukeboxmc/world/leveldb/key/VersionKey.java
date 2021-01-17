package org.jukeboxmc.world.leveldb.key;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class VersionKey extends BaseKey {

    protected VersionKey( int chunkX, int chunkZ ) {
        super( chunkX, chunkZ, DATA_VERSION );
    }

    public static VersionKey create( int chunkX, int chunkZ ) {
        return new VersionKey( chunkX, chunkZ );
    }
}
