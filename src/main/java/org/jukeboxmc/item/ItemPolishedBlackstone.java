package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstone extends Item {

    public ItemPolishedBlackstone() {
        super( -291 );
    }

    @Override
    public BlockPolishedBlackstone getBlock() {
        return new BlockPolishedBlackstone();
    }
}
