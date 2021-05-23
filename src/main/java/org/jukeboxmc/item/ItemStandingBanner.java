package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStandingBanner extends Item {

    public ItemStandingBanner() {
        super( 176 );
    }

    @Override
    public BlockStandingBanner getBlock() {
        return new BlockStandingBanner();
    }
}
