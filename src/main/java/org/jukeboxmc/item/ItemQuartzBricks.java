package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockQuartzBricks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemQuartzBricks extends Item {

    public ItemQuartzBricks() {
        super( -304 );
    }

    @Override
    public BlockQuartzBricks getBlock() {
        return new BlockQuartzBricks();
    }
}
