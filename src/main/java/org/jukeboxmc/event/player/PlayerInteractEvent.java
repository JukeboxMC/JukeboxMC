package org.jukeboxmc.event.player;

import lombok.ToString;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
@ToString
public class PlayerInteractEvent extends PlayerEvent implements Cancellable {

    private Action action;
    private Item item;
    private Block clickedBlock;

    /**
     * Creates a new {@link PlayerInteractEvent}
     *
     * @param player       who has interacted with something
     * @param action       which represents the interaction type
     * @param item         which is the interaction item
     * @param clickedBlock which stands for the block the player clicked at
     */
    public PlayerInteractEvent( Player player, Action action, Item item, Block clickedBlock ) {
        super( player );

        this.action = action;
        this.item = item;
        this.clickedBlock = clickedBlock;
    }

    /**
     * Retrieves the {@link Action}
     *
     * @return a fresh {@link Action}
     */
    public Action getAction() {
        return this.action;
    }

    /**
     * Retrieves the interaction {@link Item}
     *
     * @return a fresh {@link Item}
     */
    public Item getItem() {
        return this.item;
    }

    /**
     * Modifies the interaction {@link Item}
     *
     * @param item which should be modified
     */
    public void setItem( Item item ) {
        this.item = item;
    }

    /**
     * Retrieves the {@link Block} the player clicked at
     *
     * @return a fresh {@link Block}
     */
    public Block getClickedBlock() {
        return this.clickedBlock;
    }

    /**
     * Representation of the type of interaction which takes place in this {@link PlayerInteractEvent}
     */
    public enum Action {
        LEFT_CLICK_AIR,
        RIGHT_CLICK_AIR,
        LEFT_CLICK_BLOCK,
        RIGHT_CLICK_BLOCK;
    }
}