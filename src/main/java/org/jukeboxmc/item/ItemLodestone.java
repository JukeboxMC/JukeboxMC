package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLodestone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLodestone extends Item {

    public ItemLodestone() {
        super( -222 );
    }

    @Override
    public BlockLodestone getBlock() {
        return new BlockLodestone();
    }
}
