package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBrickStairs extends Item {

    public ItemBrickStairs() {
        super( 108 );
    }

    @Override
    public BlockBrickStairs getBlock() {
        return new BlockBrickStairs();
    }
}
