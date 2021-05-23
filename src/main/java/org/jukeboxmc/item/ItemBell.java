package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBell;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBell extends Item {

    public ItemBell() {
        super( -206 );
    }

    @Override
    public BlockBell getBlock() {
        return new BlockBell();
    }
}
