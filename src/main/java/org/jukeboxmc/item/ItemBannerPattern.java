package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBannerPattern extends Item {

    public ItemBannerPattern() {
        super( 613 );
    }

    @Override
    public BlockStandingBanner getBlock() {
        return new BlockStandingBanner();
    }
}
