package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBannerPattern extends Item {

    public ItemBannerPattern() {
        super ( "minecraft:banner_pattern" );
    }

    @Override
    public BlockStandingBanner getBlock() {
        return new BlockStandingBanner();
    }

    @Override
    public int getMaxAmount() {
        return 16;
    }
}
