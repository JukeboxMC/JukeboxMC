package jukeboxmc.event.player;

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

    /**
     * Creates a new {@link PlayerBucketEvent}
     *
     * @param player       who did an action with a bucket item
     * @param bucket       which represents the bucket item
     * @param itemInHand   which stands for the item which was hold by the player in their hand
     * @param clickedBlock which is the block the player clicked at
     */
    public PlayerBucketEvent( Player player, Item bucket, Item itemInHand, Block clickedBlock ) {
        super( player );

        this.bucket = bucket;
        this.itemInHand = itemInHand;
        this.clickedBlock = clickedBlock;
    }

    /**
     * Retrieves the item which was hold in the players hand
     * after the regular bucket item has updated
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
     * Modifies the block the player clicked at
     *
     * @param clickedBlock which will be changed
     */
    public void setClickedBlock( Block clickedBlock ) {
        this.clickedBlock = clickedBlock;
    }
}