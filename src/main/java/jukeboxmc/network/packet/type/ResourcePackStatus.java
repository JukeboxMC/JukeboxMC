package jukeboxmc.network.packet.type;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum ResourcePackStatus {

    REFUSED,
    SEND_PACKS,
    HAVE_ALL_PACKS,
    COMPLETED;

    public static ResourcePackStatus retrieveResponseStatusById( int responseStatusId ) {
        for ( ResourcePackStatus status : ResourcePackStatus.values() ) {
            if ( status.ordinal() == ( responseStatusId - 1 ) ) {
                return status;
            }
        }
        return null;
    }
}
