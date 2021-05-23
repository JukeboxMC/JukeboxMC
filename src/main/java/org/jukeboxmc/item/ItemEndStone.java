package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockEndStone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEndStone extends Item {

    public ItemEndStone() {
        super( 121 );
    }

    @Override
    public BlockEndStone getBlock() {
        return new BlockEndStone();
    }
}
