package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBirchStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchStairs extends Item {

    public ItemBirchStairs() {
        super( "minecraft:birch_stairs", 135 );
    }

    @Override
    public Block getBlock() {
        return new BlockBirchStairs();
    }
}
