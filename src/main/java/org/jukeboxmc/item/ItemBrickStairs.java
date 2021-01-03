package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBrickBlock;
import org.jukeboxmc.block.BlockBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBrickStairs extends Item {

    public ItemBrickStairs() {
        super( "minecraft:brick_stairs", 108 );
    }

    @Override
    public Block getBlock() {
        return new BlockBrickStairs();
    }
}
