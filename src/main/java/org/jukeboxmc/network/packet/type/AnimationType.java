package org.jukeboxmc.network.packet.type;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum AnimationType {

    NO_ACTION( 0 ),
    SWING_ARM( 1 ),
    WAKE_UP( 3 ),
    CRITICAL_HIT( 4 ),
    MAGIC_CRITICAL_HIT( 5 ),
    ROW_RIGHT( 128 ),
    ROW_LEFT( 129 );

    private int id;

    AnimationType( int id ) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static AnimationType getActionFromId( int id ) {
        switch ( id ) {
            case 1:
                return SWING_ARM;
            case 3:
                return WAKE_UP;
            case 4:
                return CRITICAL_HIT;
            case 5:
                return MAGIC_CRITICAL_HIT;
            case 128:
                return ROW_RIGHT;
            case 129:
                return ROW_LEFT;
            default:
                return NO_ACTION;
        }
    }
}
