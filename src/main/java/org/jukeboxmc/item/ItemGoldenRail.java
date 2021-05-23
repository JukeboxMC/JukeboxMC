package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGoldenRail;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGoldenRail extends Item {

    public ItemGoldenRail() {
        super( 27 );
    }

    @Override
    public BlockGoldenRail getBlock() {
        return new BlockGoldenRail();
    }
}
