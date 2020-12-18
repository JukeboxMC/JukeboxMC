package org.jukeboxmc.utils;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Utils {

    public static int blockToChunk( int value ) {
        return value >> 4;
    }

    public static long toLong( int x, int z ) {
        return ( (long) x << 32 ) + z - Integer.MIN_VALUE;
    }

    public static int log2(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        return 31 - Integer.numberOfLeadingZeros(n);
    }

}
