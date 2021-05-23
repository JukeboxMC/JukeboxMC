package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPrismarineBricksStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPrismarineBricksStairs extends Item {

    public ItemPrismarineBricksStairs() {
        super( -4 );
    }

    @Override
    public BlockPrismarineBricksStairs getBlock() {
        return new BlockPrismarineBricksStairs();
    }
}
