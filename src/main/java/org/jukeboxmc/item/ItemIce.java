package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockIce;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemIce extends Item {

    public ItemIce() {
        super( 79 );
    }

    @Override
    public BlockIce getBlock() {
        return new BlockIce();
    }
}
