package org.jukeboxmc.network.raknet.protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BitFlags {

    public static final byte VALID = (byte) 0x80;
    public static final byte ACK = 0x40;
    public static final byte NACK = 0x20;
    public static final byte SPLIT = 0x10;

}
