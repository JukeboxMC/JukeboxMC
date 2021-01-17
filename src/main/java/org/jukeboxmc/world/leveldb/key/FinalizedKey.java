package org.jukeboxmc.world.leveldb.key;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FinalizedKey extends BaseKey {

    protected FinalizedKey( int chunkX, int chunkZ) {
        super( chunkX, chunkZ, (byte) 0x2F );
    }

    public static FinalizedKey create( int chunkX, int chunkZ ) {
        return new FinalizedKey( chunkX, chunkZ );
    }
}
