package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstoneBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstoneBrickStairs extends Item {

    public ItemPolishedBlackstoneBrickStairs() {
        super ( "minecraft:polished_blackstone_brick_stairs" );
    }

    @Override
    public BlockPolishedBlackstoneBrickStairs getBlock() {
        return new BlockPolishedBlackstoneBrickStairs();
    }
}
