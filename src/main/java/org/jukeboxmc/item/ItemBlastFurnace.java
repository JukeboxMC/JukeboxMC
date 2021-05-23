package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBlastFurnace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlastFurnace extends Item {

    public ItemBlastFurnace() {
        super(  -196 );
    }

    @Override
    public BlockBlastFurnace getBlock() {
        return new BlockBlastFurnace();
    }
}
