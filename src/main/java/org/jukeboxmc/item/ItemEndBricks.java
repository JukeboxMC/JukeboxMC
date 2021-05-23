package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockEndBricks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEndBricks extends Item {

    public ItemEndBricks() {
        super( 206 );
    }

    @Override
    public BlockEndBricks getBlock() {
        return new BlockEndBricks();
    }
}
