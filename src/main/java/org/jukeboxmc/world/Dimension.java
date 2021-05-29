package org.jukeboxmc.world;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum Dimension {

    OVERWORLD( (byte) 0 ),
    NETHER( (byte) 1 ),
    THE_END( (byte) 2 );

    private byte id;

    Dimension( byte id ) {
        this.id = id;
    }

    public byte getId() {
        return this.id;
    }
}
