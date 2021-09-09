package jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerToggleGlideEvent extends PlayerEvent implements Cancellable {

    private final boolean isGliding;

    /**
     * Creates a new {@link PlayerToggleGlideEvent}
     *
     * @param player    who toggled gliding
     * @param isGliding whether the player is gliding
     */
    public PlayerToggleGlideEvent( Player player, boolean isGliding ) {
        super( player );

        this.isGliding = isGliding;
    }

    /**
     * Retreives whether the player is gliding or not
     *
     * @return whether the player is gliding
     */
    public boolean isGliding() {
        return this.isGliding;
    }
}