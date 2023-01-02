package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerExperienceChangeEvent extends PlayerEvent implements Cancellable {

    private final int oldExperience;
    private final int oldLevel;

    private int newExperience;
    private int newLevel;


    /**
     * Creates a new {@link PlayerEvent}
     *
     * @param player who represents the player which comes with this event
     */
    public PlayerExperienceChangeEvent( Player player, int oldExperience, int oldLevel, int newExperience, int newLevel ) {
        super( player );
        this.oldExperience = oldExperience;
        this.oldLevel = oldLevel;
        this.newExperience = newExperience;
        this.newLevel = newExperience;
    }

    public int getOldExperience() {
        return this.oldExperience;
    }

    public int getOldLevel() {
        return this.oldLevel;
    }

    public int getNewExperience() {
        return this.newExperience;
    }

    public void setNewExperience( int newExperience ) {
        this.newExperience = newExperience;
    }

    public int getNewLevel() {
        return this.newLevel;
    }

    public void setNewLevel( int newLevel ) {
        this.newLevel = newLevel;
    }
}
