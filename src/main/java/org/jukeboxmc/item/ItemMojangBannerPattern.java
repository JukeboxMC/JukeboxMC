package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
class ItemMojangBannerPattern extends Item {

    public ItemMojangBannerPattern() {
        super( 574 );
    }

    @Override
    public BlockStandingBanner getBlock() {
        return new BlockStandingBanner();
    }
}
