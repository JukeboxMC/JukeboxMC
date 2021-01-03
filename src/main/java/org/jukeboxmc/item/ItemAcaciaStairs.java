package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAcaciaStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaStairs extends Item {

    public ItemAcaciaStairs() {
        super( "minecraft:acacia_stairs", 163 );
    }

    @Override
    public Block getBlock() {
        return new BlockAcaciaStairs();
    }
}
