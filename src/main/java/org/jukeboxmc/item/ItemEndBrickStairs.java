package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockEndBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEndBrickStairs extends Item {

    public ItemEndBrickStairs() {
        super( -178 );
    }

    @Override
    public BlockEndBrickStairs getBlock() {
        return new BlockEndBrickStairs();
    }
}
