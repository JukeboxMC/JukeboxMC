package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonStairs extends Item {

    public ItemCrimsonStairs() {
        super( "minecraft:crimson_stairs", -254 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonStairs();
    }
}
