package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFlowerBannerPattern extends Item {

    public ItemFlowerBannerPattern() {
        super( 571 );
    }

    @Override
    public BlockStandingBanner getBlock() {
        return new BlockStandingBanner();
    }
}
