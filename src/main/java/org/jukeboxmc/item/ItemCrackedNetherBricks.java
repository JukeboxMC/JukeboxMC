package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrackedNetherBricks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrackedNetherBricks extends Item {

    public ItemCrackedNetherBricks() {
        super( -303 );
    }

    @Override
    public BlockCrackedNetherBricks getBlock() {
        return new BlockCrackedNetherBricks();
    }
}
