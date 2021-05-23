package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSoulSand;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSoulSand extends Item {

    public ItemSoulSand() {
        super( 88 );
    }

    @Override
    public BlockSoulSand getBlock() {
        return new BlockSoulSand();
    }
}
