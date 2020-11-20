package org.jukeboxmc.network.raknet.utils;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Reliability {

    public static boolean reliable( int id ) {
        switch ( id ) {
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }

    public static boolean sequencedOrOrdered( int id ) {
        switch ( id ) {
            case 1:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }

    public static boolean sequenced( int id ) {
        switch ( id ) {
            case 1:
            case 4:
                return true;
            default:
                return false;
        }
    }

}
