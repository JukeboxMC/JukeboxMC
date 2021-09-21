package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerFoodLevelChangeEvent extends PlayerEvent implements Cancellable {

    private int foodLevel;
    private final float saturationLevel;

    /**
     * Creates a new {@link PlayerEvent}
     *
     * @param player who represents the player which comes with this event
     */
    public PlayerFoodLevelChangeEvent( Player player, int foodLevel, float saturationLevel ) {
        super( player );
        this.foodLevel = foodLevel;
        this.saturationLevel = saturationLevel;
    }

    public int getFoodLevel() {
        return this.foodLevel;
    }

    public void setFoodLevel( int foodLevel ) {
        this.foodLevel = foodLevel;
    }

    public float getSaturationLevel() {
        return this.saturationLevel;
    }

}
