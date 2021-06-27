package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPiglinBannerPattern extends Item {

    public ItemPiglinBannerPattern() {
        super ( "minecraft:piglin_banner_pattern" );
    }

    @Override
    public BlockStandingBanner getBlock() {
        return new BlockStandingBanner();
    }
}
