package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedMushroom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedMushroom extends Item {

    public ItemRedMushroom() {
        super( 40 );
    }

    @Override
    public BlockRedMushroom getBlock() {
        return new BlockRedMushroom();
    }
}
