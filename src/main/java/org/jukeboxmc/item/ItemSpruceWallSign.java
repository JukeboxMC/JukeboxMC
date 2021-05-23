package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSpruceWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpruceWallSign extends Item {

    public ItemSpruceWallSign() {
        super( -182 );
    }

    @Override
    public BlockSpruceWallSign getBlock() {
        return new BlockSpruceWallSign();
    }
}
