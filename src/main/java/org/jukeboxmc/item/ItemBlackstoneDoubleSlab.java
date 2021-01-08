package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBlackstoneDoubleSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlackstoneDoubleSlab extends Item {

    public ItemBlackstoneDoubleSlab() {
        super( "minecraft:blackstone_double_slab", -283 );
    }

    @Override
    public BlockBlackstoneDoubleSlab getBlock() {
        return new BlockBlackstoneDoubleSlab();
    }
}
