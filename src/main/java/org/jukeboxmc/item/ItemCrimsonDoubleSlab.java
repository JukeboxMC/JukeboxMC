package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonDoubleSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonDoubleSlab extends Item {

    public ItemCrimsonDoubleSlab() {
        super( "minecraft:crimson_double_slab", -266 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonDoubleSlab();
    }
}
