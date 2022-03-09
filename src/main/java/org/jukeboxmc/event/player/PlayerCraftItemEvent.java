package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerCraftItemEvent extends PlayerEvent implements Cancellable {

    private final List<Item> input;
    private final Item result;

    /**
     * Creates a new {@link PlayerEvent}
     *
     * @param player who represents the player which comes with this event
     */
    public PlayerCraftItemEvent( Player player, List<Item> input, Item result ) {
        super( player );
        this.input = input;
        this.result = result;
    }

    public List<Item> getInput() {
        return this.input;
    }

    public Item getResult() {
        return this.result;
    }
}
