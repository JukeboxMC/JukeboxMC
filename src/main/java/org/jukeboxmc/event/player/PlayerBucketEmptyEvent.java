package org.jukeboxmc.event.player;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class PlayerBucketEmptyEvent extends PlayerBucketEvent {

    private Block placedBlock;

    /**
     * Creates a new {@link PlayerBucketEmptyEvent}
     *
     * @param player       who did an action with a bucket item
     * @param bucket       which represents the bucket item
     * @param itemInHand   which stands for the item which was hold by the player in their hand
     * @param clickedBlock which is the block the player clicked at
     * @param placedBlock  which is the block which will be placed
     */
    public PlayerBucketEmptyEvent( Player player, Item bucket, Item itemInHand, Block clickedBlock, Block placedBlock ) {
        super( player, bucket, itemInHand, clickedBlock );

        this.placedBlock = placedBlock;
    }

    /**
     * Retrieves the block which will be placed
     *
     * @return a fresh {@link Block}
     */
    public Block getPlacedBlock() {
        return this.placedBlock;
    }

    /**
     * Modifies the block which will be placed
     *
     * @param placedBlock which will be placed
     */
    public void setPlacedBlock( Block placedBlock ) {
        this.placedBlock = placedBlock;
    }
}