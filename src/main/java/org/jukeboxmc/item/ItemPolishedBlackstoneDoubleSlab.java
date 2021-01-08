package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstoneDoubleSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstoneDoubleSlab extends Item {

    public ItemPolishedBlackstoneDoubleSlab() {
        super( "minecraft:polished_blackstone_double_slab", -294 );
    }

    @Override
    public BlockPolishedBlackstoneDoubleSlab getBlock() {
        return new BlockPolishedBlackstoneDoubleSlab();
    }
}
