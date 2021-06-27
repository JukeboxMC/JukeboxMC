package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedDeepslateDoubleSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedDeepslateDoubleSlab extends Item{

    public ItemPolishedDeepslateDoubleSlab() {
        super( "minecraft:polished_deepslate_double_slab" );
    }

    @Override
    public BlockPolishedDeepslateDoubleSlab getBlock() {
        return new BlockPolishedDeepslateDoubleSlab();
    }
}
