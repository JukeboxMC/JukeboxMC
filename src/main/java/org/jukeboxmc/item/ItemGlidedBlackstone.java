package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGildedBlackstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGlidedBlackstone extends Item {

    public ItemGlidedBlackstone() {
        super( "minecraft:gilded_blackstone", -281 );
    }

    @Override
    public BlockGildedBlackstone getBlock() {
        return new BlockGildedBlackstone();
    }
}
