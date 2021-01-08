package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstoneBrickDoubleSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstoneBrickDoubleSlab extends Item {

    public ItemPolishedBlackstoneBrickDoubleSlab() {
        super( "minecraft:polished_blackstone_brick_double_slab", -285 );
    }

    @Override
    public BlockPolishedBlackstoneBrickDoubleSlab getBlock() {
        return new BlockPolishedBlackstoneBrickDoubleSlab();
    }
}
