package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkPrismarineStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkPrismarineStairs extends Item {

    public ItemDarkPrismarineStairs() {
        super( "minecraft:dark_prismarine_stairs", -3 );
    }

    @Override
    public BlockDarkPrismarineStairs getBlock() {
        return new BlockDarkPrismarineStairs();
    }
}
