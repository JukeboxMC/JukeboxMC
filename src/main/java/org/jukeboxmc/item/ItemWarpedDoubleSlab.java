package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDoubleWarpedSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedDoubleSlab extends Item {

    public ItemWarpedDoubleSlab() {
        super( "minecraft:warped_double_slab", -267 );
    }

    @Override
    public BlockDoubleWarpedSlab getBlock() {
        return new BlockDoubleWarpedSlab();
    }
}
