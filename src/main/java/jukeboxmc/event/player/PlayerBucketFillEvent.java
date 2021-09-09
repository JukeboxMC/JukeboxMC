package jukeboxmc.event.player;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class PlayerBucketFillEvent extends PlayerBucketEvent {

    public PlayerBucketFillEvent( Player player, Item bucket, Item itemInHand, Block clickedBlock ) {
        super( player, bucket, itemInHand, clickedBlock );
    }
}