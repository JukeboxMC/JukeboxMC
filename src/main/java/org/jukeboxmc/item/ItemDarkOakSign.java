package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkOakStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakSign extends Item {

    public ItemDarkOakSign() {
        super ( "minecraft:dark_oak_sign" );
    }

    @Override
    public BlockDarkOakStandingSign getBlock() {
        return new BlockDarkOakStandingSign();
    }

    @Override
    public int getMaxAmount() {
        return 16;
    }
}
