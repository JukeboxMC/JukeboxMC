package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDoubleCrimsonSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonDoubleSlab extends Item {

    public ItemCrimsonDoubleSlab() {
        super( "minecraft:crimson_double_slab", -266 );
    }

    @Override
    public BlockDoubleCrimsonSlab getBlock() {
        return new BlockDoubleCrimsonSlab();
    }
}
