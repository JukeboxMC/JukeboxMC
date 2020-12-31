package org.jukeboxmc.entity.metadata;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum MetadataFlag {

    INDEX( 0 ),
    NAMETAG( 4 ),
    AIR( 7 ),
    PLAYER_FLAGS( 26 ),
    SCALE( 38 ),
    MAX_AIR( 42 ),
    BOUNDINGBOX_WIDTH( 53 ),
    BOUNDINGBOX_HEIGHT( 54 );

    private int id;

    MetadataFlag( int id ) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
