package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockEndBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEndBrickStairs extends Item {

    public ItemEndBrickStairs() {
        super( "minecraft:end_brick_stairs", -178 );
    }

    @Override
    public BlockEndBrickStairs getBlock() {
        return new BlockEndBrickStairs();
    }
}
