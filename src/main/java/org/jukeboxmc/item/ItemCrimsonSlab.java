package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonSlab extends Item {

    public ItemCrimsonSlab() {
        super( -264 );
    }

    @Override
    public BlockCrimsonSlab getBlock() {
        return new BlockCrimsonSlab();
    }
}
