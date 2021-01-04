package org.jukeboxmc.entity.metadata;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum EntityFlag {

    ON_FIRE( 0 ),
    SNEAKING( 1 ),
    SPRINTING( 3 ),
    ACTION( 4 ),
    SHOW_NAMETAG( 14 ),
    SHOW_ALWAYS_NAMETAG( 15 ),
    HAS_COLLISION( 47 ),
    AFFECTED_BY_GRAVITY( 48 );

    private int id;

    EntityFlag( int id ) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
