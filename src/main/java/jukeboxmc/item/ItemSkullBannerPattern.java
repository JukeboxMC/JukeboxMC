package jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingBanner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSkullBannerPattern extends Item {

    public ItemSkullBannerPattern() {
        super ( "minecraft:skull_banner_pattern" );
    }

    @Override
    public BlockStandingBanner getBlock() {
        return new BlockStandingBanner();
    }
}
