package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPackedIce;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPackedIce extends Item {

    public ItemPackedIce() {
        super( 174 );
    }

    @Override
    public BlockPackedIce getBlock() {
        return new BlockPackedIce();
    }
}
