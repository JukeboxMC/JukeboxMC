package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstoneStairs extends Item {

    public ItemPolishedBlackstoneStairs() {
        super ( "minecraft:polished_blackstone_stairs" );
    }

    @Override
    public BlockPolishedBlackstoneStairs getBlock() {
        return new BlockPolishedBlackstoneStairs();
    }
}
