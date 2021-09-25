package org.jukeboxmc.event.player;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerDeathEvent extends PlayerEvent {

    private String deathMessage;
    private boolean dropInventory;
    private List<Item> drops;

    /**
     * Creates a new {@link PlayerEvent}
     *
     * @param player who represents the player which comes with this event
     */
    public PlayerDeathEvent( Player player, String deathMessage, boolean dropInventory, List<Item> drops ) {
        super( player );
        this.deathMessage = deathMessage;
        this.dropInventory = dropInventory;
        this.drops = drops;
    }

    public String getDeathMessage() {
        return this.deathMessage;
    }

    public void setDeathMessage( String deathMessage ) {
        this.deathMessage = deathMessage;
    }

    public boolean isDropInventory() {
        return this.dropInventory;
    }

    public void setDropInventory( boolean dropInventory ) {
        this.dropInventory = dropInventory;
    }

    public List<Item> getDrops() {
        return this.drops;
    }

    public void setDrops( List<Item> drops ) {
        this.drops = drops;
    }
}
