package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCake;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCake extends Item {

    public ItemCake() {
        super( 415 );
    }

    @Override
    public BlockCake getBlock() {
        return new BlockCake();
    }
}
