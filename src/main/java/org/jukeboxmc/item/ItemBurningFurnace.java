package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGlowingFurnace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBurningFurnace extends Item {

    public ItemBurningFurnace() {
        super( 62 );
    }

    @Override
    public BlockGlowingFurnace getBlock() {
        return new BlockGlowingFurnace();
    }
}
