package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLever;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLever extends Item {

    public ItemLever() {
        super( 69 );
    }

    @Override
    public BlockLever getBlock() {
        return new BlockLever();
    }
}
