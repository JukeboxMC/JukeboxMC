package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeadbush;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeadbush extends Item {

    public ItemDeadbush() {
        super( 32 );
    }

    @Override
    public BlockDeadbush getBlock() {
        return new BlockDeadbush();
    }
}
