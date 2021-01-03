package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDioriteStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDioriteStairs extends Item {

    public ItemDioriteStairs() {
        super( "minecraft:diorite_stairs", -170 );
    }

    @Override
    public Block getBlock() {
        return new BlockDioriteStairs();
    }
}
