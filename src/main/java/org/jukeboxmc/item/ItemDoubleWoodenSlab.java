package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDoubleWoodenSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoubleWoodenSlab extends Item {

    public ItemDoubleWoodenSlab() {
        super( 157 );
    }

    @Override
    public BlockDoubleWoodenSlab getBlock() {
        return new BlockDoubleWoodenSlab();
    }
}
