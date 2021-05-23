package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedWarpedStem;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedWarpedStem extends Item {

    public ItemStrippedWarpedStem() {
        super( -241 );
    }

    @Override
    public BlockStrippedWarpedStem getBlock() {
        return new BlockStrippedWarpedStem();
    }
}
