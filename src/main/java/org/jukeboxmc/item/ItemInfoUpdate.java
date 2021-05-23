package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockInfoUpdate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemInfoUpdate extends Item {

    public ItemInfoUpdate() {
        super( 248 );
    }

    @Override
    public BlockInfoUpdate getBlock() {
        return new BlockInfoUpdate();
    }
}
