package jukeboxmc.player.info;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum Device {

    ANDROID( 1 ),
    IOS( 2 ),
    OSX( 3 ),
    AMAZON( 4 ),
    GEAR_VR( 5 ),
    HOLOLENS( 6 ),
    WINDOWS( 7 ),
    WINDOWS_32( 8 ),
    DEDICATED( 9 ),
    TVOS( 10 ),
    PLAYSTATION( 11 ),
    NINTENDO( 12 ),
    XBOX( 13 ),
    WINDOWS_PHONE( 14 );

    private final int id;

    Device( int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Device getDevice( int id ) {
        switch ( id ) {
            case 1:
                return ANDROID;
            case 2:
                return IOS;
            case 3:
                return OSX;
            case 4:
                return AMAZON;
            case 5:
                return GEAR_VR;
            case 6:
                return HOLOLENS;
            case 7:
                return WINDOWS;
            case 8:
                return WINDOWS_32;
            case 9:
                return DEDICATED;
            case 10:
                return TVOS;
            case 11:
                return PLAYSTATION;
            case 12:
                return NINTENDO;
            case 13:
                return XBOX;
            case 14:
                return WINDOWS_PHONE;
            default:
                return null;
        }
    }
}
