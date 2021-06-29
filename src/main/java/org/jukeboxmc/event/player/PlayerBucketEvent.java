package org.jukeboxmc.event.player;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public abstract class PlayerBucketEvent extends PlayerEvent implements Cancellable {

    private Item bucket;
    private Item itemInHand;
    private Block clickedBlock;
    private Block placedBlock;

    /**
     * Creates a new {@link PlayerBucketEvent}
     *
     * @param player       who did an action with a bucket item
     * @param bucket       which represents the bucket item
     * @param itemInHand   which stands for the item which was hold by the player in their hand
     * @param clickedBlock which is the block the player clicked at
     * @param placedBlock  which is the block which will be placed
     */
    public PlayerBucketEvent(Player player, Item bucket, Item itemInHand, Block clickedBlock, Block placedBlock) {
        super(player);

        this.bucket = bucket;
        this.itemInHand = itemInHand;
        this.clickedBlock = clickedBlock;
        this.placedBlock = placedBlock;
    }

    /**
     * Retrieves the bucket {@link Item}
     *
     * @return a fresh {@link Item}
     */
    public Item getBucket() {
        return this.bucket;
    }

    /**
     * Retrieves the item which was hold in the players hand
     *
     * @return a fresh {@link Item}
     */
    public Item getItemInHand() {
        return this.itemInHand;
    }

    /**
     * Retrieves the block the player clicked at
     *
     * @return a fresh {@link Block}
     */
    public Block getClickedBlock() {
        return this.clickedBlock;
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
    public void setPlacedBlock(Block placedBlock) {
        this.placedBlock = placedBlock;
    }

    /**
     * Modifies the block the player clicked at
     *
     * @param clickedBlock which will be changed
     */
    public void setClickedBlock(Block clickedBlock) {
        this.clickedBlock = clickedBlock;
    }
}