package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkOakStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakStairs extends Item {

    public ItemDarkOakStairs() {
        super( "minecraft:dark_oak_stairs", 164 );
    }

    @Override
    public BlockDarkOakStairs getBlock() {
        return new BlockDarkOakStairs();
    }
}
