package org.jukeboxmc.event.player;

import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerQuitEvent extends PlayerEvent {

    private String quitMessage;

    /**
     * Creates a new {@link PlayerQuitEvent}
     *
     * @param player      who has left the server
     * @param quitMessage which will be sent when the player leaves the server
     */
    public PlayerQuitEvent( Player player, String quitMessage ) {
        super( player );

        this.quitMessage = quitMessage;
    }

    /**
     * Retrieves the leave message
     *
     * @return a fresh {@link String}
     */
    public String getQuitMessage() {
        return this.quitMessage;
    }

    /**
     * Modifies the leave message
     *
     * @param quitMessage which should be modified
     */
    public void setQuitMessage( String quitMessage ) {
        this.quitMessage = quitMessage;
    }
}