package jukeboxmc.event.player;

import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerJoinEvent extends PlayerEvent {

    private String joinMessage;

    /**
     * Creates a new {@link PlayerJoinEvent}
     *
     * @param player      who joined the server
     * @param joinMessage which will be send when the player joins
     */
    public PlayerJoinEvent( Player player, String joinMessage ) {
        super( player );

        this.joinMessage = joinMessage;
    }

    /**
     * Retrieves the player join message
     *
     * @return a fresh {@link String}
     */
    public String getJoinMessage() {
        return this.joinMessage;
    }

    /**
     * Modifies the player join message
     *
     * @param joinMessage which should be modified
     */
    public void setJoinMessage( String joinMessage ) {
        this.joinMessage = joinMessage;
    }
}