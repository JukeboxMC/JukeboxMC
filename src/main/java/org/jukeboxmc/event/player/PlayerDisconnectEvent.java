package org.jukeboxmc.event.player;

import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class PlayerDisconnectEvent extends PlayerEvent {

    private DisconnectReason disconnectReason;

    private String disconnectMessage = "";

    /**
     * Creates a new {@link PlayerDisconnectEvent}
     *
     * @param player            who was disconnected from the server
     * @param disconnectReason  which represents the reason which describes the cause of the disconnect
     * @param disconnectMessage which stands for the message shown on the disconnect screen
     */
    public PlayerDisconnectEvent( Player player, DisconnectReason disconnectReason, String disconnectMessage ) {
        super( player );

        this.disconnectMessage = disconnectMessage;
        this.disconnectReason = disconnectReason;
    }

    /**
     * Retrieves the disconnect message
     *
     * @return a fresh {@link String}
     */
    public String getDisconnectMessage() {
        return this.disconnectMessage;
    }

    /**
     * Modifies the disconnect message
     *
     * @param disconnectMessage which should be set
     */
    public void setDisconnectMessage( String disconnectMessage ) {
        this.disconnectMessage = disconnectMessage;
    }

    /**
     * Retrieves this {@link DisconnectReason}
     *
     * @return a fresh {@link DisconnectReason}
     */
    public DisconnectReason getDisconnectReason() {
        return this.disconnectReason;
    }

    /**
     * Representation of the reason which describes the cause of the players disconnect
     */
    public enum DisconnectReason {
        SERVER_SHUTDOWN( "Server shutdown" ),
        WORLD_UNLOADED( "World was unloaded" ),
        UNKNOWN( "Unknown" );

        private String message;

        DisconnectReason( String message ) {
            this.message = message;
        }

        /**
         * Retrieves the {@link DisconnectReason} state by their constant message
         *
         * @param message which is a constant defined for each {@link DisconnectReason}
         * @return a fresh {@link DisconnectReason}
         */
        public static DisconnectReason getReasonByMessage( String message ) {
            for ( DisconnectReason disconnectReason : DisconnectReason.values() ) {
                if ( disconnectReason.message.equalsIgnoreCase( message ) ) {
                    return disconnectReason;
                }
            }

            return DisconnectReason.UNKNOWN;
        }
    }
}