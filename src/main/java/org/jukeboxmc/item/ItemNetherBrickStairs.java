package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherBrickStairs extends Item {

    public ItemNetherBrickStairs() {
        super( 114 );
    }

    @Override
    public BlockNetherBrickStairs getBlock() {
        return new BlockNetherBrickStairs();
    }
}
