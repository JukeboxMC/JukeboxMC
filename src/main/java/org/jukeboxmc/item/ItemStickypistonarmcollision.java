package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStickypistonarmcollision;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStickypistonarmcollision extends Item {

    public ItemStickypistonarmcollision() {
        super( -217 );
    }

    @Override
    public BlockStickypistonarmcollision getBlock() {
        return new BlockStickypistonarmcollision();
    }
}
