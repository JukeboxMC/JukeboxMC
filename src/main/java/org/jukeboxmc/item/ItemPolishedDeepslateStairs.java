package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedDeepslateStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedDeepslateStairs extends Item {

    public ItemPolishedDeepslateStairs() {
        super( "minecraft:polished_deepslate_stairs" );
    }

    @Override
    public BlockPolishedDeepslateStairs getBlock() {
        return new BlockPolishedDeepslateStairs();
    }
}
