package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonSlab extends Item {

    public ItemCrimsonSlab() {
        super( "minecraft:crimson_slab", -264 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonSlab();
    }
}
