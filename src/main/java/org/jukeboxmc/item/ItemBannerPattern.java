package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingBanner;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBannerPattern extends Item implements Burnable {

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

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }
}
