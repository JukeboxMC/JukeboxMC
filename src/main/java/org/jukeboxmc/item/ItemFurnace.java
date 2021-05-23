package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFurnace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFurnace extends Item {

    public ItemFurnace() {
        super( 61 );
    }

    @Override
    public BlockFurnace getBlock() {
        return new BlockFurnace();
    }
}
