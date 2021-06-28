package org.jukeboxmc.event.player;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class PlayerBucketEmptyEvent extends PlayerBucketEvent {

    public PlayerBucketEmptyEvent(Player player, Item bucket, Item itemInHand, Block clickedBlock, Block placedBlocK) {
        super(player, bucket, itemInHand, clickedBlock, placedBlocK);
    }
}