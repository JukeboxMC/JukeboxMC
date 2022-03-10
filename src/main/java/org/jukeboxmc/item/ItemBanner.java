package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingBanner;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBanner extends Item implements Burnable {

    public ItemBanner() {
        super ( "minecraft:banner" );
    }

    @Override
    public BlockStandingBanner getBlock() {
        return new BlockStandingBanner();
    }

    @Override
    public int getMaxAmount() {
        return 16;
    }

    public void setColor( BannerColor bannerColor ) {
        this.setMeta( bannerColor.ordinal() );
    }

    public BannerColor getColor() {
        return BannerColor.values()[this.getMeta()];
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 300 );
    }

    public enum BannerColor {
        BLACK,
        RED,
        GREEN,
        BROWN,
        BLUE,
        PURPLE,
        CYAN,
        SILVER,
        GRAY,
        PINK,
        LIME,
        YELLOW,
        LIGHT_BLUE,
        MAGENTA,
        ORANGE,
        WHITE
    }
}
