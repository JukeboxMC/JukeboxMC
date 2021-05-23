package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedNetherBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedNetherBrickStairs extends Item {

    public ItemRedNetherBrickStairs() {
        super( -184 );
    }

    @Override
    public BlockRedNetherBrickStairs getBlock() {
        return new BlockRedNetherBrickStairs();
    }
}
