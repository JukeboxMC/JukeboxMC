package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkOakTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakTrapdoor extends Item {

    public ItemDarkOakTrapdoor() {
        super( -147 );
    }

    @Override
    public BlockDarkOakTrapdoor getBlock() {
        return new BlockDarkOakTrapdoor();
    }
}
