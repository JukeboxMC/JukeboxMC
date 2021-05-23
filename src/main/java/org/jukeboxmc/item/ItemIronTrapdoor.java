package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockIronTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemIronTrapdoor extends Item {

    public ItemIronTrapdoor() {
        super( 167 );
    }

    @Override
    public BlockIronTrapdoor getBlock() {
        return new BlockIronTrapdoor();
    }
}
