package org.jukeboxmc.utils;

import it.unimi.dsi.fastutil.longs.LongComparator;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ChunkComparator implements LongComparator {

    private final Player player;

    public ChunkComparator( Player player ) {
        this.player = player;
    }

    public static int distance( int centerX, int centerZ, int x, int z) {
        int dx = centerX - x;
        int dz = centerZ - z;
        return dx * dx + dz * dz;
    }

    @Override
    public int compare(long o1, long o2) {
        int x1 = Utils.fromHashX(o1);
        int z1 = Utils.fromHashZ(o1);
        int x2 = Utils.fromHashX(o2);
        int z2 = Utils.fromHashZ(o2);
        int spawnX = this.player.getChunkX();
        int spawnZ = this.player.getChunkZ();

        int diffX1 = Math.abs( x1 - spawnX );
        int diffZ1 = Math.abs( z1 - spawnZ );

        int diffX2 = Math.abs( x2 - spawnX );
        int diffZ2 = Math.abs( z2 - spawnZ );

        return Integer.compare( diffX1 + diffZ1, diffX2 + diffZ2 );
        //return Integer.compare(distance(spawnX, spawnZ, x1, z1), distance(spawnX, spawnZ, x2, z2));
    }
}
