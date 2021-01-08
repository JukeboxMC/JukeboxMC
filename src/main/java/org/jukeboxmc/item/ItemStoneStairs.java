package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStoneStairs extends Item {

    public ItemStoneStairs() {
        super( "minecraft:stone_stairs", 67 );
    }

    @Override
    public BlockStoneStairs getBlock() {
        return new BlockStoneStairs();
    }
}
