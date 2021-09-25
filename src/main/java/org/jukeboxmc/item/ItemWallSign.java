package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockOakWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWallSign extends Item {

    public ItemWallSign() {
        super ( "minecraft:wall_sign" );
    }

    @Override
    public BlockOakWallSign getBlock() {
        return new BlockOakWallSign();
    }

    @Override
    public int getMaxAmount() {
        return 16;
    }
}
